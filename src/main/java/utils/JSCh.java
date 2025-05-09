package utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JSCh {
    private Session session;

    public void connectSSH() throws Exception {

        String sshHost = "helios.cs.ifmo.ru";
        String sshUser = "s467055";
        int sshPort = 2222;
        int localPort = 5432;
        String remoteHost = "localhost";
        int remotePort = 5432;

        JSch jsch = new JSch();
        session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(System.getenv("PASS"));

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        session.setPortForwardingL(localPort, remoteHost, remotePort);

        System.out.println("Подключение к БД было успешно!");
    }

    public void disconnectSSH() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("Выполнено отключение от БД...");
        }
    }
}
