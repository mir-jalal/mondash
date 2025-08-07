package net.mirjalal.mondash.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Setter
@Getter
@Document(indexName = "splitkey-alerts")
public class Alert {
    @Id
    private String id;

    private String latestErrorMessage;
    private String monitorName;
    private String monitorUrl;
    private String statusMessage;
    
}
