package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Role;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;
import com.liceolapaz.mgr.jugadores2ev.recu_di.service.UserService;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class UsersController {

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer> idCol;
    @FXML private TableColumn<User, String> usernameCol;
    @FXML private TableColumn<User, String> emailCol;
    @FXML private TableColumn<User, Role> roleCol;
    @FXML private TableColumn<User, Integer> favCarCol;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;

    private final UserService userService = new UserService();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        checkPermissions();
        loadUsers();
    }

    private void setupTableColumns() {
        idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        usernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        roleCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRole()));

        favCarCol.setCellValueFactory(cellData -> {
            int favId = cellData.getValue().getFavoriteCarId();
            return new SimpleObjectProperty<>(favId > 0 ? favId : null);
        });
    }

    private void checkPermissions() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            deleteButton.setDisable(true);
            deleteButton.setVisible(false);
            statusLabel.setText("Read-only mode. Only ADMINs can delete users.");
            statusLabel.setStyle("-fx-text-fill: #f1c40f;");
        }
    }

    private void loadUsers() {
        Task<List<User>> loadTask = new Task<>() {
            @Override
            protected List<User> call() {
                return userService.getAllUsers();
            }
        };

        loadTask.setOnSucceeded(e -> {
            userList.setAll(loadTask.getValue());
            usersTable.setItems(userList);
        });

        new Thread(loadTask).start();
    }

    @FXML
    private void handleDeleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            statusLabel.setText("Please select a user to delete.");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        if (selectedUser.getId() == SessionManager.getCurrentUser().getId()) {
            statusLabel.setText("Security warning: You cannot delete your own account.");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        Task<Boolean> deleteTask = new Task<>() {
            @Override
            protected Boolean call() {
                return userService.deleteUser(selectedUser.getId());
            }
        };

        deleteTask.setOnSucceeded(e -> {
            if (deleteTask.getValue()) {
                statusLabel.setText("User deleted successfully.");
                statusLabel.setStyle("-fx-text-fill: #2ecc71;");
                loadUsers();
            } else {
                statusLabel.setText("Error deleting user.");
                statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        });

        new Thread(deleteTask).start();
    }
}