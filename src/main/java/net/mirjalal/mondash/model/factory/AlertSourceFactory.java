package net.mirjalal.mondash.model.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import net.mirjalal.mondash.encryption.AesEncryptor;
import net.mirjalal.mondash.encryption.Encryptor;
import net.mirjalal.mondash.model.AlertParameter;
import net.mirjalal.mondash.model.AlertSource;
import net.mirjalal.mondash.model.dto.AlertSourceCreateDto;
import net.mirjalal.mondash.model.dto.AlertSourceGetDto;
import net.mirjalal.mondash.model.dto.AlertSourceParameterDto;

@Component
public class AlertSourceFactory {

    private final Encryptor encryptor;

    public AlertSourceFactory(AesEncryptor aesEncryptor) {
        this.encryptor = aesEncryptor;
    }

    public AlertSource createAlertSource(AlertSourceCreateDto alertSourceCreateRequest) {
        AlertSource alertSource = new AlertSource();
        alertSource.setName(alertSourceCreateRequest.getName());

        List<AlertParameter> alertParameters = new ArrayList<>();
        alertSourceCreateRequest.getParameters().stream()
          .forEach(p -> {
            AlertParameter alertParameter = new AlertParameter();
            alertParameter.setAlertSource(alertSource);
            alertParameter.setKey(p.getKey());
            
            Boolean isSensitive = p.getSensitive();
            isSensitive = isSensitive == null ? false : isSensitive;
            alertParameter.setSensitive(isSensitive);
            
            String value = isSensitive ? encryptor.encrypt(p.getValue()) : p.getValue();
            alertParameter.setValue(value);

            alertParameters.add(alertParameter);
          });
        alertSource.setParameters(alertParameters);
        
        return alertSource;
    }

    public AlertSource decryptAlertSource(AlertSource alertSource) {
      List<AlertParameter> alertParameters = alertSource.getParameters();
      alertParameters.forEach(alertParameter -> {
        if (alertParameter.isSensitive()) {
          alertParameter.setValue(this.encryptor.decrypt(alertParameter.getValue()));
        }
      });
      alertSource.setParameters(alertParameters);
      return alertSource;
    }

    public AlertSourceGetDto createAlertSourceGet(AlertSource alertSource) {
        AlertSourceGetDto alertSourceGet = new AlertSourceGetDto();
        alertSourceGet.setId(alertSource.getId());
        alertSourceGet.setName(alertSource.getName());

        List<AlertSourceParameterDto> alertParameters = new ArrayList<>();
        alertSource.getParameters().stream()
          .forEach(parameter -> {
            if(parameter.isSensitive()) {
                alertParameters.add(new AlertSourceParameterDto(parameter.getKey(), "<encrypted>", parameter.isSensitive()));
            }
            else {
                alertParameters.add(new AlertSourceParameterDto(parameter.getKey(), parameter.getValue(), parameter.isSensitive()));
            }
          });
          alertSourceGet.setParameters(alertParameters);

        return alertSourceGet;
    }
}
