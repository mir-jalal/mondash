package net.mirjalal.mondash.alert;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import net.mirjalal.mondash.alert.strategy.AlertStrategy;
import net.mirjalal.mondash.alert.strategy.ElasticAlert;
import net.mirjalal.mondash.model.AlertSource;
import net.mirjalal.mondash.model.AlertParameter;

public final class AlertClientFactory {

    private AlertClientFactory() {}

    public static AlertClient createAlertClient(AlertSource alertSource) {
        AlertClient alertClient = new AlertClient();

        AlertStrategy alertStrategy;
        AlertParameter alertParameter = (AlertParameter) alertSource.getParameter("type");

        try {
            String alertType = alertParameter.getValue();

            if (alertType.equals("elastic")) {
                alertStrategy = new ElasticAlert(alertSource);
                alertClient.setAlertStrategy(alertStrategy);
            }
        } catch (KeyManagementException | CertificateException | KeyStoreException | NoSuchAlgorithmException
                | IOException e) {
            System.out.println("Cannot create alert client");
        }

        return alertClient;
    }
}
