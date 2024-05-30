package Controllers;

import Dao.*;
import Models.*;
import Service.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class MainController {
    //region Объявления FMXL
    public TextField fullNameTextField;
    public TextField dataTextField;
    public TableView<Appeals> tableAppeals;
    public TableColumn<Appeals, Integer> fullNameColumn;
    public TableColumn<Appeals, String> dataColumn;
    public TableView<Diagnoses> tableDiagnoses;
    public TableColumn<Diagnoses, String> nameColumn;
    public TableView<Patients> tablePatients;
    public TableColumn<Patients, String> lastNameColumn2;
    public TableColumn<Patients, String> firstNameColumn2;
    public TableColumn<Patients, String> middleNameColumn1;
    public TableColumn<Patients, Integer> ageColumn;
    public TableColumn<Patients, String> addressColumn;
    public TableColumn<Patients, Integer> patientCategoryIdColumn;
    public TextField lastNameTextField1;
    public TextField firstNameTextField1;
    public TextField middleNameTextField1;
    public TextField ageTextField;
    public TextField addressTextField;
    public TextField patientCategoryIdTextField;
    public TextField nameTextField;
    public TableView<Doctors> tableDoctors;
    public TableColumn<Doctors, String> lastNameColumn;
    public TableColumn<Doctors, String> firstNameColumn;
    public TableColumn<Doctors, String> middleNameColumn;
    public TableColumn<Doctors, Integer> doctorSpecialtiesIdColumn;
    public TableColumn<Doctors, Integer> doctorCategoryIdColumn;
    public TextField lastNameTextField;
    public TextField firstNameTextField;
    public TextField middleNameTextField;
    public TextField doctorSpecialtiesIdTextField;
    public TextField doctorCategoryIdTextField;
    public TableView<Treatments> tableTreatments;
    public TableColumn<Treatments, Integer> appealIdColumn;
    public TableColumn<Treatments, Integer> doctorIdColumn;
    public TableColumn<Treatments, Integer> diagnosisIdColumn;
    public TableColumn<Treatments, Integer> priceColumn;
    public TextField appealIdTextField;
    public TextField doctorIdTextField;
    public TextField diagnosisIdTextField;
    public TextField priceTextField;
    public TableView<PatientCategories> tablePatientCategories;
    public TableColumn<PatientCategories, String> lastNameColumn1;
    public TableColumn<PatientCategories, Integer> firstNameColumn1;
    public TextField nameTextField3;
    public TextField discountTextField;
    public TableView<DoctorCategories> tableDoctorCategories;
    public TableColumn<DoctorCategories, String> nameColumn1;
    public TextField nameTextField1;
    public TableView<DoctorSpecialties> tableDoctorSpecialties;
    public TableColumn<DoctorSpecialties, String> nameColumn2;
    public TextField nameTextField2;
    public ComboBox<Patients> PatientIdComboBox;
    public ComboBox<DoctorSpecialties> DoctorSpecialtyIdComboBox;
    public ComboBox<DoctorCategories> DoctorCategoryIdComboBox;
    public ComboBox<PatientCategories> PatientCategoryIdComboBox;
    public ComboBox<Appeals> AppealIdComboBox;
    public ComboBox<Doctors> DoctorIdComboBox;
    public ComboBox<Diagnoses> DiagnosisIdComboBox;
    public TableView<Patients> tablePatients1;
    public TableColumn<Patients, String> lastNameColumn21;
    public TableColumn<Patients, Integer> ageColumn1;
    public TableColumn<Patients, String> addressColumn1;
    public DatePicker dataPicker;
    public TableView<Doctors> tableDoctors1;
    public TableColumn<Doctors, String> lastNameColumn3;
    public TableColumn<Doctors, String> doctorSpecialtiesIdColumn1;
    public TableView<Doctors> tableDoctors11;
    public TableColumn<Doctors, String> lastNameColumn31;
    public TableColumn<Doctors, String> doctorCategoriesIdColumn11;
    //endregion

    //region Объявления сервисов, листов и дао
    private ObservableList<Appeals> appeals = FXCollections.observableArrayList();
    private ServiceAppeals serviceAppeals;
    private ObservableList<Diagnoses> diagnoses = FXCollections.observableArrayList();
    private ServiceDiagnoses serviceDiagnoses;
    private ObservableList<Patients> patients = FXCollections.observableArrayList();
    private ObservableList<Patients> patients1 = FXCollections.observableArrayList();
    private ServicePatients servicePatients;
    private ObservableList<Doctors> doctors = FXCollections.observableArrayList();
    private ObservableList<Doctors> doctors1 = FXCollections.observableArrayList();
    private ObservableList<Doctors> doctors2 = FXCollections.observableArrayList();
    private ServiceDoctors serviceDoctors;
    private ObservableList<Treatments> treatments = FXCollections.observableArrayList();
    private ServiceTreatments serviceTreatmnets;
    private ObservableList<PatientCategories> patientCategories = FXCollections.observableArrayList();
    private ServicePatientCategories servicePatientCategories;
    private ObservableList<DoctorCategories> doctorCategories = FXCollections.observableArrayList();
    private ServiceDoctorCategories serviceDoctorCategories;
    private ObservableList<DoctorSpecialties> doctorSpecialties = FXCollections.observableArrayList();
    private ServiceDoctorSpecialties serviceDoctorSpecialties;
    private PatientsDao patientDao;
    private PatientCategoriesDao patientCategoriesDao;
    private DoctorCategoriesDao doctorCategoriesDao;
    private DoctorSpecialtiesDao doctorSpecialtiesDao;
    private DoctorsDao doctorsDao;
    private AppealsDao appealsDao;
    private DiagnosesDao diagnosesDao;
    //endregion

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    public MainController() {
        patientDao = new PatientsDao();
        patientCategoriesDao = new PatientCategoriesDao();
        doctorCategoriesDao = new DoctorCategoriesDao();
        doctorSpecialtiesDao = new DoctorSpecialtiesDao();
        doctorsDao = new DoctorsDao();
        appealsDao = new AppealsDao();
        diagnosesDao = new DiagnosesDao();
        serviceAppeals = new ServiceAppeals(new AppealsDao());
        serviceDiagnoses = new ServiceDiagnoses(new DiagnosesDao());
        servicePatients = new ServicePatients(new PatientsDao());
        serviceDoctors = new ServiceDoctors(new DoctorsDao());
        serviceTreatmnets = new ServiceTreatments(new TreatmentsDao());
        servicePatientCategories = new ServicePatientCategories(new PatientCategoriesDao());
        serviceDoctorCategories = new ServiceDoctorCategories(new DoctorCategoriesDao());
        serviceDoctorSpecialties = new ServiceDoctorSpecialties(new DoctorSpecialtiesDao());
    }

    @FXML
    protected void initialize() {
        PatientIdComboBox.setItems(patients);
        DoctorSpecialtyIdComboBox.setItems(doctorSpecialties);
        DoctorCategoryIdComboBox.setItems(doctorCategories);
        PatientCategoryIdComboBox.setItems(patientCategories);
        AppealIdComboBox.setItems(appeals);
        DoctorIdComboBox.setItems(doctors);
        DiagnosisIdComboBox.setItems(diagnoses);

        java.time.LocalDate currentDate = java.time.LocalDate.now();
        dataPicker.setValue(currentDate);
    }

    //region Работа с Tab панелью
    public void loadPatientsByCategory(int patientCategoryId) {
        try {
            tablePatients1.getItems().clear();
            patients1.addAll(servicePatientCategories.FindByPatientId(patientCategoryId));
            if(patients1 != null) {
                lastNameColumn21.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().toString()));
                ageColumn1.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getAge()));
                addressColumn1.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getAddress()));
                tablePatients1.setItems(patients1);
            } else {
                tablePatients1.getItems().clear();
            }
            logger.debug("Данные успешно получены");
        } catch (SQLException e) {
            logger.error("Ошибка получения данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные клиента");
            alert.setContentText("Пожалуйста, попробуйте снова.");
            alert.showAndWait();
        }
    }

    @FXML
    void clickPatientCategories(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Категории пациентов'");
        tablePatients1.getItems().clear();
        PatientCategories selectedPatientCategory = tablePatientCategories.getSelectionModel().getSelectedItem();
        if (selectedPatientCategory != null) {
            logger.debug("Строка выбрана");
            int patientCategoryId = selectedPatientCategory.getId();
            nameTextField3.setText(selectedPatientCategory.getName());
            discountTextField.setText(String.valueOf(selectedPatientCategory.getDiscount()));
            loadPatientsByCategory(patientCategoryId);
        }
    }

    public void loadDoctorsByCategory(int doctorCategoryId) {
        try {
            tableDoctors1.getItems().clear();
            doctors1.addAll(serviceDoctorCategories.FindByDoctorId(doctorCategoryId));
            if(doctors1 != null) {
                lastNameColumn3.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().toString()));
                doctorSpecialtiesIdColumn1.setCellValueFactory(item -> new SimpleStringProperty(
                        doctorSpecialtiesDao.findById(item.getValue().getDoctorSpecialtyId()).getName()
                ));
                tableDoctors1.setItems(doctors1);
            } else {
                tableDoctors1.getItems().clear();
            }
            logger.debug("Данные успешно получены");
        } catch (SQLException e) {
            logger.error("Ошибка получения данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные клиента");
            alert.setContentText("Пожалуйста, попробуйте снова.");
            alert.showAndWait();
        }
    }

    @FXML
    void clickDoctorCategories(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Категории врачей'");
        tableDoctors1.getItems().clear();
        DoctorCategories selectedDoctorCategory = tableDoctorCategories.getSelectionModel().getSelectedItem();
        if (selectedDoctorCategory != null) {
            logger.debug("Строка выбрана");
            int doctorCategoryId = selectedDoctorCategory.getId();
            nameTextField1.setText(selectedDoctorCategory.getName());
            loadDoctorsByCategory(doctorCategoryId);
        }
    }

    public void loadDoctorsBySpecialty(int doctorSpecialtyId) {
        try {
            tableDoctors11.getItems().clear();
            doctors2.addAll(serviceDoctorSpecialties.FindByDoctorId(doctorSpecialtyId));
            if(doctors2 != null) {
                lastNameColumn31.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().toString()));
                doctorCategoriesIdColumn11.setCellValueFactory(item -> new SimpleStringProperty(
                        doctorCategoriesDao.findById(item.getValue().getDoctorCategoryId()).getName()
                ));
                tableDoctors11.setItems(doctors2);
            } else {
                tableDoctors11.getItems().clear();
            }
            logger.debug("Данные успешно получены");
        } catch (SQLException e) {
            logger.error("Ошибка получения данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные клиента");
            alert.setContentText("Пожалуйста, попробуйте снова.");
            alert.showAndWait();
        }
    }

    @FXML
    void clickDoctorSpecialties(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Специальности врачей'");
        tableDoctors11.getItems().clear();
        DoctorSpecialties selectedDoctorSpecialty = tableDoctorSpecialties.getSelectionModel().getSelectedItem();
        if (selectedDoctorSpecialty != null) {
            logger.debug("Строка выбрана");
            int doctorSpecialtyId = selectedDoctorSpecialty.getId();
            nameTextField2.setText(selectedDoctorSpecialty.getName());
            loadDoctorsBySpecialty(doctorSpecialtyId);
        }
    }

    @FXML
    void clickDiagnoses(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Диагнозы'");
        Diagnoses selectedDiagnoses = tableDiagnoses.getSelectionModel().getSelectedItem();
        if (selectedDiagnoses != null) {
            logger.debug("Строка выбрана");
            nameTextField.setText(selectedDiagnoses.getName());
        }
    }

    @FXML
    void clickPatients(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Пациенты'");
        Patients selectedPatient = tablePatients.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            logger.debug("Строка выбрана");
            lastNameTextField1.setText(selectedPatient.getLastName());
            firstNameTextField1.setText(selectedPatient.getFirstName());
            middleNameTextField1.setText(selectedPatient.getMiddleName());
            ageTextField.setText(String.valueOf(selectedPatient.getAge()));
            addressTextField.setText(selectedPatient.getAddress());
        }
    }

    @FXML
    void clickDoctors(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Врачи'");
        Doctors selectedDoctor = tableDoctors.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            logger.debug("Строка выбрана");
            lastNameTextField.setText(selectedDoctor.getLastName());
            firstNameTextField.setText(selectedDoctor.getFirstName());
            middleNameTextField.setText(selectedDoctor.getMiddleName());
        }
    }

    @FXML
    void clickAppeals(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Обращения'");
        Appeals selectedAppeal = tableAppeals.getSelectionModel().getSelectedItem();
        if (selectedAppeal != null) {
            logger.debug("Строка выбрана");
            String dateString = String.valueOf(selectedAppeal.getData());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            dataPicker.setValue(date);
        }
    }

    @FXML
    void clickTreatments(MouseEvent mouseEvent) {
        logger.info("Выбрана таблица 'Лечение'");
        Treatments selectedTreatment = tableTreatments.getSelectionModel().getSelectedItem();
        if (selectedTreatment != null) {
            logger.debug("Строка выбрана");
            priceTextField.setText(String.valueOf(selectedTreatment.getPrice()));
        }
    }

    @FXML
    void diagnosesTab(Event event) {
        logger.info("Выбрана вкладка 'Диагнозы'");
        tableDiagnoses.getItems().clear();
        diagnoses.addAll(serviceDiagnoses.ViewField());
        nameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        tableDiagnoses.setItems(diagnoses);
    }

    @FXML
    void patientsTab(Event event) {
        logger.info("Выбрана вкладка 'Пациенты'");
        tablePatients.getItems().clear();
        patients.addAll(servicePatients.ViewField());
        lastNameColumn2.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getLastName()));
        firstNameColumn2.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getFirstName()));
        middleNameColumn1.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getMiddleName()));
        ageColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getAge()));
        addressColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getAddress()));
        patientCategoryIdColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                patientCategoriesDao.findById(item.getValue().getPatientCategoryId()).getName()
        ));
        tablePatients.setItems(patients);
    }

    @FXML
    void doctorsTab(Event event) {
        logger.info("Выбрана вкладка 'Врачи'");
        tableDoctors.getItems().clear();
        doctors.addAll(serviceDoctors.ViewField());
        lastNameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getLastName()));
        firstNameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getFirstName()));
        middleNameColumn.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getMiddleName()));
        doctorSpecialtiesIdColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                doctorSpecialtiesDao.findById(item.getValue().getDoctorSpecialtyId()).getName()
        ));
        doctorCategoryIdColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                doctorCategoriesDao.findById(item.getValue().getDoctorCategoryId()).getName()
        ));
        tableDoctors.setItems(doctors);
    }

    @FXML
    void appealsTab(Event event) {
        logger.info("Выбрана вкладка 'Обращения'");
        tableAppeals.getItems().clear();
        appeals.addAll(serviceAppeals.ViewField());
        dataColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getData()));
        fullNameColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                patientDao.findById(item.getValue().getPatientId()).toString()
        ));
        tableAppeals.setItems(appeals);
    }

    @FXML
    void TreatmentsTab(Event event) {
        logger.info("Выбрана вкладка 'Лечение'");
        tableTreatments.getItems().clear();
        treatments.addAll(serviceTreatmnets.ViewField());
        appealIdColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                appealsDao.findById(item.getValue().getAppealId()).getId()
        ));
        doctorIdColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                doctorsDao.findById(item.getValue().getDoctorId()).toString()
        ));
        diagnosisIdColumn.setCellValueFactory(item -> new SimpleObjectProperty(
                diagnosesDao.findById(item.getValue().getDiagnosisId()).getName()
        ));
        priceColumn.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getPrice()));
        tableTreatments.setItems(treatments);
    }

    @FXML
    void patientCategoriesTab(Event event) {
        logger.info("Выбрана вкладка 'Категории пациентов'");
        tablePatients1.getItems().clear();
        tablePatientCategories.getItems().clear();
        patientCategories.addAll(servicePatientCategories.ViewField());
        lastNameColumn1.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        firstNameColumn1.setCellValueFactory(item -> new SimpleObjectProperty(item.getValue().getDiscount()));
        tablePatientCategories.setItems(patientCategories);
    }

    @FXML
    void doctorCategoriesTab(Event event) {
        logger.info("Выбрана вкладка 'Категории врачей'");
        tableDoctorCategories.getItems().clear();
        doctorCategories.addAll(serviceDoctorCategories.ViewField());
        nameColumn1.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        tableDoctorCategories.setItems(doctorCategories);
    }

    @FXML
    void doctorSpecialtiesTab(Event event) {
        logger.info("Выбрана вкладка 'Специальности врачей'");
        tableDoctorSpecialties.getItems().clear();
        doctorSpecialties.addAll(serviceDoctorSpecialties.ViewField());
        nameColumn2.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName()));
        tableDoctorSpecialties.setItems(doctorSpecialties);
    }
    //endregion

    //region Работа с Buttons
    @FXML
    void addAppeal(ActionEvent event) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tableAppeals.getItems().clear();
        if (PatientIdComboBox.getItems().isEmpty() || dataPicker.getValue() == null) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            Date data = Date.valueOf(dataPicker.getValue());
            int patientId = PatientIdComboBox.getSelectionModel().getSelectedItem().getId();
            Appeals appeals1 = serviceAppeals.createAppeals(data, patientId);
            tableAppeals.getItems().add(appeals1);
            tableAppeals.getItems().clear();
            appeals.addAll(serviceAppeals.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteAppeal(ActionEvent event) {
        logger.info("Нажатие на кнопку 'Удалить'");
        Appeals appeals1 = tableAppeals.getSelectionModel().getSelectedItem();
        if (appeals1 != null) {
            serviceAppeals.deleteAppeals(appeals1);
            tableAppeals.getItems().remove(appeals1);
            tableAppeals.getItems().clear();
            appeals.addAll(serviceAppeals.ViewField());
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateAppeal(ActionEvent event) {
        logger.info("Нажатие на кнопку 'Изменить'");
        Appeals selectedAppeal = tableAppeals.getSelectionModel().getSelectedItem();
        if (selectedAppeal == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            Date data = Date.valueOf(dataPicker.getValue());
            int patientId = PatientIdComboBox.getSelectionModel().getSelectedItem().getId();
            selectedAppeal.setData(data);
            selectedAppeal.setPatientId(patientId);
            serviceAppeals.updateAppeals(selectedAppeal, data, patientId);
            tableAppeals.getItems().clear();
            appeals.addAll(serviceAppeals.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addDiagnosis(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tableDiagnoses.getItems().clear();
        if (nameTextField.getText().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField.getText()));
            Diagnoses diagnoses1 = serviceDiagnoses.createDiagnoses(name);
            tableDiagnoses.getItems().add(diagnoses1);
            tableDiagnoses.getItems().clear();
            diagnoses.addAll(serviceDiagnoses.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteDiagnosis(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        Diagnoses diagnoses1 = tableDiagnoses.getSelectionModel().getSelectedItem();
        if (diagnoses1 != null) {
            serviceDiagnoses.deleteDiagnoses(diagnoses1);
            tableDiagnoses.getItems().remove(diagnoses1);
            tableDiagnoses.getItems().clear();
            diagnoses.addAll(serviceDiagnoses.ViewField());
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateDiagnosis(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        Diagnoses selectedDiagnoses = tableDiagnoses.getSelectionModel().getSelectedItem();
        if (selectedDiagnoses == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField.getText()));
            selectedDiagnoses.setName(name);
            serviceDiagnoses.updateDiagnoses(selectedDiagnoses, name);
            tableDiagnoses.getItems().clear();
            diagnoses.addAll(serviceDiagnoses.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addPatient(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tablePatients.getItems().clear();
        if (lastNameTextField1.getText().isEmpty() || firstNameTextField1.getText().isEmpty() || middleNameTextField1.getText().isEmpty() ||
        ageTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || PatientCategoryIdComboBox.getItems().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            String lastName = String.valueOf((lastNameTextField1.getText()));
            String firstName = String.valueOf((firstNameTextField1.getText()));
            String middleName = String.valueOf((middleNameTextField1.getText()));
            int age = Integer.parseInt((ageTextField.getText()));
            String address = String.valueOf((addressTextField.getText()));
            int patientCategoryId = PatientCategoryIdComboBox.getSelectionModel().getSelectedItem().getId();
            Patients patients1 = servicePatients.createPatients(lastName, firstName, middleName, age, address, patientCategoryId);
            tablePatients.getItems().add(patients1);
            tablePatients.getItems().clear();
            patients.addAll(servicePatients.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deletePatient(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        Patients patients1 = tablePatients.getSelectionModel().getSelectedItem();
        if (patients1 != null) {
            servicePatients.deletePatients(patients1);
            tablePatients.getItems().remove(patients1);
            tablePatients.getItems().clear();
            patients.addAll(servicePatients.ViewField());
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updatePatient(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        Patients selectedPatient = tablePatients.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            String lastName = String.valueOf((lastNameTextField1.getText()));
            String firstName = String.valueOf((firstNameTextField1.getText()));
            String middleName = String.valueOf((middleNameTextField1.getText()));
            int age = Integer.parseInt((ageTextField.getText()));
            String address = String.valueOf((addressTextField.getText()));
            int patientCategoryId = PatientCategoryIdComboBox.getSelectionModel().getSelectedItem().getId();
            selectedPatient.setLastName(lastName);
            selectedPatient.setFirstName(firstName);
            selectedPatient.setMiddleName(middleName);
            selectedPatient.setAge(age);
            selectedPatient.setAddress(address);
            selectedPatient.setPatientCategoryId(patientCategoryId);
            servicePatients.updatePatients(selectedPatient, lastName, firstName, middleName, age, address, patientCategoryId);
            tablePatients.getItems().clear();
            patients.addAll(servicePatients.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addDoctor(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        if (lastNameTextField.getText().isEmpty() || firstNameTextField.getText().isEmpty() || middleNameTextField.getText().isEmpty() ||
        DoctorSpecialtyIdComboBox.getItems().isEmpty() || DoctorCategoryIdComboBox.getItems().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            String lastName = String.valueOf((lastNameTextField.getText()));
            String firstName = String.valueOf((firstNameTextField.getText()));
            String middleName = String.valueOf((middleNameTextField.getText()));
            int doctorSpecialtyId = DoctorSpecialtyIdComboBox.getSelectionModel().getSelectedItem().getId();
            int doctorCategoryId = DoctorCategoryIdComboBox.getSelectionModel().getSelectedItem().getId();
            Doctors doctors1 = serviceDoctors.createDoctors(lastName, firstName, middleName, doctorSpecialtyId, doctorCategoryId);
            tableDoctors.getItems().add(doctors1);
            tableDoctors.getItems().clear();
            doctors.addAll(serviceDoctors.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteDoctor(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        Doctors doctors1 = tableDoctors.getSelectionModel().getSelectedItem();
        if (doctors1 != null) {
            serviceDoctors.deleteDoctors(doctors1);
            tableDoctors.getItems().remove(doctors1);
            tableDoctors.getItems().clear();
            doctors.addAll(serviceDoctors.ViewField());
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateDoctor(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        Doctors selectedDoctor = tableDoctors.getSelectionModel().getSelectedItem();
        if (selectedDoctor == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            String lastName = String.valueOf((lastNameTextField.getText()));
            String firstName = String.valueOf((firstNameTextField.getText()));
            String middleName = String.valueOf((middleNameTextField.getText()));
            int doctorSpecialtyId = DoctorSpecialtyIdComboBox.getSelectionModel().getSelectedItem().getId();
            int doctorCategoryId = DoctorCategoryIdComboBox.getSelectionModel().getSelectedItem().getId();
            selectedDoctor.setLastName(lastName);
            selectedDoctor.setFirstName(firstName);
            selectedDoctor.setMiddleName(middleName);
            selectedDoctor.setDoctorSpecialtyId(doctorSpecialtyId);
            selectedDoctor.setDoctorCategoryId(doctorCategoryId);
            serviceDoctors.updateDoctors(selectedDoctor, lastName, firstName, middleName, doctorSpecialtyId, doctorCategoryId);
            tableDoctors.getItems().clear();
            doctors.addAll(serviceDoctors.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addTreatment(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tableTreatments.getItems().clear();
        if (AppealIdComboBox.getItems().isEmpty() || DoctorIdComboBox.getItems().isEmpty() || DiagnosisIdComboBox.getItems().isEmpty() ||
                priceTextField.getText().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            int appealId = AppealIdComboBox.getSelectionModel().getSelectedItem().getId();
            int doctorId = DoctorIdComboBox.getSelectionModel().getSelectedItem().getId();
            int diagnosisId = DiagnosisIdComboBox.getSelectionModel().getSelectedItem().getId();
            int price = Integer.parseInt((priceTextField.getText()));
            Treatments treatments1 = serviceTreatmnets.createTreatmnets(appealId, doctorId, diagnosisId, price);
            tableTreatments.getItems().add(treatments1);
            tableTreatments.getItems().clear();
            treatments.addAll(serviceTreatmnets.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteTreatment(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        Treatments treatments1 = tableTreatments.getSelectionModel().getSelectedItem();
        if (treatments1 != null) {
            serviceTreatmnets.deleteTreatmnets(treatments1);
            tableTreatments.getItems().remove(treatments1);
            tableTreatments.getItems().clear();
            treatments.addAll(serviceTreatmnets.ViewField());
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateTreatment(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        Treatments selectedTreatment = tableTreatments.getSelectionModel().getSelectedItem();
        if (selectedTreatment == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            int appealId = AppealIdComboBox.getSelectionModel().getSelectedItem().getId();
            int doctorId = DoctorIdComboBox.getSelectionModel().getSelectedItem().getId();
            int diagnosisId = DiagnosisIdComboBox.getSelectionModel().getSelectedItem().getId();
            int price = Integer.parseInt((priceTextField.getText()));
            selectedTreatment.setAppealId(appealId);
            selectedTreatment.setDoctorId(doctorId);
            selectedTreatment.setDiagnosisId(diagnosisId);
            selectedTreatment.setPrice(price);
            serviceTreatmnets.updateTreatmnets(selectedTreatment, appealId, doctorId, diagnosisId, price);
            tableTreatments.getItems().clear();
            treatments.addAll(serviceTreatmnets.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addPatientCategory(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tablePatientCategories.getItems().clear();
        if (nameTextField3.getText().isEmpty() || discountTextField.getText().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf(nameTextField3.getText());
            int discount = Integer.parseInt(discountTextField.getText());
            PatientCategories patientCategories1 = servicePatientCategories.createPatientCategories(name, discount);
            tablePatientCategories.getItems().add(patientCategories1);
            tablePatientCategories.getItems().clear();
            patientCategories.addAll(servicePatientCategories.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deletePatientCategory(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        PatientCategories patientCategories1 = tablePatientCategories.getSelectionModel().getSelectedItem();
        if (patientCategories1 != null) {
            servicePatientCategories.deletePatientCategories(patientCategories1);
            tablePatientCategories.getItems().remove(patientCategories1);
            tablePatientCategories.getItems().clear();
            patientCategories.addAll(servicePatientCategories.ViewField());
            tablePatients1.getItems().clear();
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updatePatientCategory(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        PatientCategories selectedPatientCategory = tablePatientCategories.getSelectionModel().getSelectedItem();
        if (selectedPatientCategory == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField3.getText()));
            int discount = Integer.parseInt(discountTextField.getText());
            selectedPatientCategory.setName(name);
            selectedPatientCategory.setDiscount(discount);
            servicePatientCategories.updatePatientCategories(selectedPatientCategory, name, discount);
            tablePatientCategories.getItems().clear();
            patientCategories.addAll(servicePatientCategories.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addDoctorCategory(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tableDoctorCategories.getItems().clear();
        if (nameTextField1.getText().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField1.getText()));
            DoctorCategories doctorCategories1 = serviceDoctorCategories.createDoctorCategories(name);
            tableDoctorCategories.getItems().add(doctorCategories1);
            tableDoctorCategories.getItems().clear();
            doctorCategories.addAll(serviceDoctorCategories.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteDoctorCategory(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        DoctorCategories doctorCategories1 = tableDoctorCategories.getSelectionModel().getSelectedItem();
        if (doctorCategories1 != null) {
            serviceDoctorCategories.deleteDoctorCategories(doctorCategories1);
            tableDoctorCategories.getItems().remove(doctorCategories1);
            tableDoctorCategories.getItems().clear();
            doctorCategories.addAll(serviceDoctorCategories.ViewField());
            tableDoctors1.getItems().clear();
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateDoctorCategory(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        DoctorCategories selectedDoctorCategory = tableDoctorCategories.getSelectionModel().getSelectedItem();
        if (selectedDoctorCategory == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Контракт не выбран");
            alert.setContentText("Пожалуйста, выберите контракт для обновления.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField1.getText()));
            selectedDoctorCategory.setName(name);
            serviceDoctorCategories.updateDoctorCategories(selectedDoctorCategory, name);
            tableDoctorCategories.getItems().clear();
            doctorCategories.addAll(serviceDoctorCategories.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void addDoctorSpecialty(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Добавить'");
        tableDoctorSpecialties.getItems().clear();
        if (nameTextField2.getText().isEmpty()) {
            logger.debug("Имеются пустые поля");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields are empty:");
            alert.setContentText("Please, fill all fields.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField2.getText()));
            DoctorSpecialties doctorSpecialties1 = serviceDoctorSpecialties.createDoctorSpecialties(name);
            tableDoctorSpecialties.getItems().add(doctorSpecialties1);
            tableDoctorSpecialties.getItems().clear();
            doctorSpecialties.addAll(serviceDoctorSpecialties.ViewField());
            logger.debug("Успешное добавление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteDoctorSpecialty(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Удалить'");
        DoctorSpecialties doctorSpecialties1 = tableDoctorSpecialties.getSelectionModel().getSelectedItem();
        if (doctorSpecialties1 != null) {
            serviceDoctorSpecialties.deleteDoctorSpecialties(doctorSpecialties1);
            tableDoctorSpecialties.getItems().remove(doctorSpecialties1);
            tableDoctorSpecialties.getItems().clear();
            doctorSpecialties.addAll(serviceDoctorSpecialties.ViewField());
            logger.debug("Объект успешно удален");
        } else {
            logger.debug("Ошибка получения объекта");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for deleting.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateDoctorSpecialty(ActionEvent actionEvent) {
        logger.info("Нажатие на кнопку 'Изменить'");
        DoctorSpecialties selectedDoctorSpecialty = tableDoctorSpecialties.getSelectionModel().getSelectedItem();
        if (selectedDoctorSpecialty == null) {
            logger.debug("Объект не выбран");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Row is not selected:");
            alert.setContentText("Please, choose row for editing.");
            alert.showAndWait();
            return;
        }
        try {
            String name = String.valueOf((nameTextField2.getText()));
            selectedDoctorSpecialty.setName(name);
            serviceDoctorSpecialties.updateDoctorSpecialties(selectedDoctorSpecialty, name);
            tableDoctorSpecialties.getItems().clear();
            doctorSpecialties.addAll(serviceDoctorSpecialties.ViewField());
            logger.debug("Успешное обновление записи");
        } catch (NumberFormatException e) {
            logger.error("Ошибка обновления записи - неверный формат данных", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Incorrect data format:");
            alert.setContentText("Please, Check all data and try again.");
            alert.showAndWait();
        }
    }
    //endregion
}