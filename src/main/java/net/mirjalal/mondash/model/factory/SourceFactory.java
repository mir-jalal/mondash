package net.mirjalal.mondash.model.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.mirjalal.mondash.encryption.AesEncryptor;
import net.mirjalal.mondash.encryption.Encryptor;
import net.mirjalal.mondash.model.AlertParameter;
import net.mirjalal.mondash.model.AlertSource;
import net.mirjalal.mondash.model.NotifierParameter;
import net.mirjalal.mondash.model.NotifierSource;
import net.mirjalal.mondash.model.Parameter;
import net.mirjalal.mondash.model.Source;
import net.mirjalal.mondash.model.dto.SourceCreateDto;
import net.mirjalal.mondash.model.dto.SourceGetDto;
import net.mirjalal.mondash.model.dto.SourceParameterDto;

@Component
public class SourceFactory {
    public enum SourceType {
      ALERT, NOTIFIER
    }

    private final Encryptor encryptor;

    public SourceFactory(AesEncryptor aesEncryptor) {
        this.encryptor = aesEncryptor;
    }

    public Source createSource(SourceCreateDto sourceCreateRequest, SourceType sourceType) {
      Source source;
      switch (sourceType) {
        case ALERT -> source = new AlertSource();
        case NOTIFIER -> source = new NotifierSource();
        default -> {
            return null;
            }
        }
      source.setName(sourceCreateRequest.name());

      List<Parameter> parameters = new ArrayList<>();
      sourceCreateRequest.parameters().stream()
        .forEach(p -> {
          Parameter parameter;

          switch(sourceType) {
            case ALERT ->  parameter = new AlertParameter();
            case NOTIFIER ->  parameter = new NotifierParameter();
            default -> {
              return;
            }
          }
          parameter.setSource(source);
          parameter.setKey(p.key());
          
          Boolean isSensitive = p.sensitive();
          isSensitive = isSensitive == null ? false : isSensitive;
          parameter.setSensitive(isSensitive);
          
          String value = isSensitive ? encryptor.encrypt(p.value()) : p.value();
          parameter.setValue(value);

          parameters.add(parameter);
        });
      source.setParameters(parameters);
        
      return source;
    }

    public Source decryptSource(Source source) {
      List<Parameter> parameters = source.getAllParameters();
      parameters.forEach(parameter -> {
        if (parameter.isSensitive()) {
          parameter.setValue(this.encryptor.decrypt(parameter.getValue()));
        }
      });
      source.setParameters(parameters);
      return source;
    }

    public SourceGetDto createSourceGet(Source source) {
        SourceGetDto sourceGet = new SourceGetDto();
        sourceGet.setId(source.getId());
        sourceGet.setName(source.getName());

        List<SourceParameterDto> alertParameters = new ArrayList<>();
        source.getAllParameters().stream()
          .forEach(parameter -> {
            if(parameter.isSensitive()) {
                alertParameters.add(new SourceParameterDto(parameter.getKey(), "<encrypted>", parameter.isSensitive()));
            }
            else {
                alertParameters.add(new SourceParameterDto(parameter.getKey(), parameter.getValue(), parameter.isSensitive()));
            }
          });
          sourceGet.setParameters(alertParameters);

        return sourceGet;
    }
}
