package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Networking.CustomServer;
import Networking.SocketEchoerThread;


public class Main extends Application {
	private Controller controller;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Gui.fxml"));
			VBox root = (VBox) loader.load();
			Scene scene = new Scene(root,640,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ChatApp");
			primaryStage.show();
			CustomServer server;
			server = new CustomServer(8080, this);
			server.start();
			controller = loader.getController();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateText(StringBuilder sb) {
		Text object = null;
		object.setText(sb.toString());
		controller.getModel().addMessage(object);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void sendSocket(SocketEchoerThread echoer) {
		controller.setSockets(echoer);
	}
}
