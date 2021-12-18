package gui.Controller;

import gui.WebServerGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class WebServerController {

    public CheckBox checkSwitch;
    public TextField serverPort;
    public Button btnWebRootDirectory;
    public Button btnMaintenanceDir;
    @FXML
    private Label welcomeText;

    @FXML
    private Label serverStatusLabel;

    @FXML
    private Label serverAddressLabel;

    @FXML
    private Label serverListeningPortLabel;

    @FXML
    private Button buttonServerStatus;

    @FXML
    private TextField setWebRootDirectoryPath;

    @FXML
    private TextField setMaintenanceRootDirectoryPath;

    @FXML
    protected void onHelloButtonClick() throws InterruptedException, IOException {
        if(WebServerGUI.webServerGUI.SERVER_STATUS.equals("RUN_SERVER")) {
            WebServerGUI.webServerGUI.SERVER_STATUS = "STOP_SERVER";
            WebServerGUI.serverSocketGUI.close();
            setTexts();
        } else {
            WebServerGUI.webServerGUI.SERVER_STATUS = "RUN_SERVER";
            setTexts();
        }
        System.out.println("CURRENT SERVER STATUS:" + WebServerGUI.webServerGUI.SERVER_STATUS);
    }

    public void initialize(){
        setTexts();
        setMaintenanceRootDirectoryPath.setText(WebServerGUI.SERVER_MAINTENANCE);
        setWebRootDirectoryPath.setText(WebServerGUI.SERVER_ROOT_WEB);
        serverPort.setText(String.valueOf(WebServerGUI.SERVER_LISTENING_PORT));
    }
    private void setTexts() {
        welcomeText.setText("Server Status: " + WebServerGUI.webServerGUI.SERVER_STATUS);

        switch (WebServerGUI.webServerGUI.SERVER_STATUS) {
            case "RUN_SERVER":
                buttonServerStatus.setText("Stop server");
                serverStatusLabel.setText("running...");
                serverAddressLabel.setText(WebServerGUI.SERVER_ADDRESS);
                serverListeningPortLabel.setText(String.valueOf(WebServerGUI.SERVER_LISTENING_PORT));
                checkSwitch.setDisable(false);
                serverPort.setDisable(true);
                btnWebRootDirectory.setDisable(true);
                setWebRootDirectoryPath.setDisable(true);
                btnMaintenanceDir.setDisable(false);
                setMaintenanceRootDirectoryPath.setDisable(false);
                break;
            case "MAINTENANCE_SERVER":
                serverStatusLabel.setText("maintenance");
                serverAddressLabel.setText(WebServerGUI.SERVER_ADDRESS);
                serverListeningPortLabel.setText(String.valueOf(WebServerGUI.SERVER_LISTENING_PORT));
                serverPort.setDisable(true);
                btnWebRootDirectory.setDisable(false);
                setWebRootDirectoryPath.setDisable(false);
                btnMaintenanceDir.setDisable(true);
                setMaintenanceRootDirectoryPath.setDisable(true);
                break;
            case "STOP_SERVER":
                buttonServerStatus.setText("Start server");
                serverStatusLabel.setText("not running");
                serverAddressLabel.setText("not running");
                serverListeningPortLabel.setText("not running");
                checkSwitch.setDisable(true);
                serverPort.setDisable(false);
                serverPort.setText(String.valueOf(WebServerGUI.SERVER_LISTENING_PORT));
                btnWebRootDirectory.setDisable(false);
                setWebRootDirectoryPath.setDisable(false);
                btnMaintenanceDir.setDisable(false);
                setMaintenanceRootDirectoryPath.setDisable(false);
                break;
        }
    }

    public void chooseDirectoryMaintenance(javafx.event.ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);

        if(file != null) {
            setMaintenanceRootDirectoryPath.setText(file.getAbsolutePath());
            WebServerGUI.SERVER_MAINTENANCE = file.getAbsolutePath();
        }
    }

    public void chooseDirectoryRoot(javafx.event.ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);

        if(file != null) {
            setWebRootDirectoryPath.setText(file.getAbsolutePath());
            WebServerGUI.SERVER_ROOT_WEB = file.getAbsolutePath();
        }
    }

    public void checkSwitchMode(ActionEvent actionEvent) {
        if(!checkSwitch.isSelected()) {
            WebServerGUI.webServerGUI.SERVER_STATUS = "RUN_SERVER";
            setTexts();
        } else {
            WebServerGUI.webServerGUI.SERVER_STATUS = "MAINTENANCE_SERVER";
            setTexts();
        }
    }

    public void setServerPort(ActionEvent actionEvent) {
         WebServerGUI.SERVER_LISTENING_PORT = Integer.parseInt(serverPort.getText());
         setTexts();
    }

    public void webRootDirAction(ActionEvent actionEvent) {
    }

    public void maintenanceDirAction(ActionEvent actionEvent) {
    }
}