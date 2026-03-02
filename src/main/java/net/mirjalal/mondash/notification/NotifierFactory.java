package net.mirjalal.mondash.notification;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import net.mirjalal.mondash.model.NotifierParameter;
import net.mirjalal.mondash.model.NotifierSource;
import net.mirjalal.mondash.notification.strategy.MatrixNotification;
import net.mirjalal.mondash.notification.strategy.NotificationStrategy;

public final class NotifierFactory {

    public static final class Type {
        public static final String MATRIX = "matrix";
    }

    private NotifierFactory() {}

    public static Notifier createNotifier( NotifierSource notifierSource ) throws NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException, IOException {
        Notifier notifier = new Notifier();
        NotificationStrategy notificationStrategy;

        NotifierParameter notifierParameter = (NotifierParameter) notifierSource.getParameter("type");

        String notifierType = notifierParameter.getValue();

        if(notifierType.equals(Type.MATRIX)) {
            notificationStrategy = new MatrixNotification(notifierSource);
            notifier.setNotificationStrategy(notificationStrategy);
        }
        
        return notifier;
    }
}
