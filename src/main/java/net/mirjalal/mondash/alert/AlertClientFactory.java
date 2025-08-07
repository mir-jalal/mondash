package net.mirjalal.mondash.alert;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Optional;

import net.mirjalal.mondash.model.AlertParameter;
import net.mirjalal.mondash.model.AlertSource;

public final class AlertClientFactory {

    public static AlertClient createAlertClient(AlertSource alertSource) {
        AlertClient alertClient = new AlertClient();

        AlertStrategy alertStrategy;
        Optional<AlertParameter> optionalAlertType = alertSource.getParameter("type");

        try {
            if (optionalAlertType.isEmpty()) {
                System.out.println("Unrecognized type. Didn't create");
            } else {
                String alertType = optionalAlertType.get().getValue();

                if (alertType.equals("elastic")) {
                    String username = alertSource.getParameter("username").get().getValue();
                    String password = alertSource.getParameter("password").get().getValue();
                    String hostname = alertSource.getParameter("hostname").get().getValue();
                    String caCert = alertSource.getParameter("caCert").get().getValue();
                    String indexName = alertSource.getParameter("indexName").get().getValue();

                    alertStrategy = new ElasticAlert(username, password, hostname, caCert, indexName);
                    alertClient.setAlertStrategy(alertStrategy);
                }
            }
        } catch (KeyManagementException | CertificateException | KeyStoreException | NoSuchAlgorithmException
                | IOException e) {
            System.out.println("Cannot create alert client");
        }

        return alertClient;
    }
}
