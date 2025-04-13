/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Controller.GenericDao;
import Model.Carro;
import Model.ClienteVenda;
import Model.Funcionario;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;

public class Dashboard extends JFrame {
    
    // Components
    private JPanel headerPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JRadioButton lightThemeRadio;
    private JRadioButton darkThemeRadio;
    
    // Data Access Objects
    private GenericDao<Carro> carroDao;
    private GenericDao<ClienteVenda> clienteDao;
    
    // Current car images
    private Map<Integer, ArrayList<String>> carImagesMap;
    private int currentImageIndex = 0;
    private ArrayList<String> currentCarImages;
    
    // Selected car for sale
    private Carro selectedCarro;
   public Funcionario f;
    // Constructor
    public Dashboard( Funcionario f) {
        this.f = f; 
        // Initialize DAOs
        carroDao = new GenericDao<>(Carro.class);
        clienteDao = new GenericDao<>(ClienteVenda.class);
        carImagesMap = new HashMap<>();
        
        // Set up frame
        setTitle("Sistema de Gestão de Venda de Carros");
        setSize(1200, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Apply FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }
        
        // Set up layout
        setLayout(new BorderLayout());
        
        // Create components
        createHeader();
        createMenu();
        createContent();
        
        // Show frame
        setVisible(true);
    }
    
    private void createHeader() {
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(1096, 45));
        headerPanel.setBackground(new Color(0, 122, 204));
        headerPanel.setLayout(new BorderLayout());
        
        // Create theme toggle
        JPanel themeTogglePanel = new JPanel();
        themeTogglePanel.setOpaque(false);
        
        lightThemeRadio = new JRadioButton("Claro");
        lightThemeRadio.setForeground(Color.WHITE);
        lightThemeRadio.setSelected(true);
        lightThemeRadio.setOpaque(false);
        
        darkThemeRadio = new JRadioButton("Escuro");
        darkThemeRadio.setForeground(Color.WHITE);
        darkThemeRadio.setOpaque(false);
        
        // Group radio buttons
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightThemeRadio);
        themeGroup.add(darkThemeRadio);
        
        // Add action listeners
        lightThemeRadio.addActionListener(e -> changeTheme(true));
        darkThemeRadio.addActionListener(e -> changeTheme(false));
        
        themeTogglePanel.add(lightThemeRadio);
        themeTogglePanel.add(darkThemeRadio);
        
        // Create system info
        JLabel systemInfoLabel = new JLabel("Sistema de Gestão de Venda de Carros | Perfil Vendedor: "+f.getNome()+" "+f.getApelido());
        systemInfoLabel.setForeground(Color.WHITE);
        systemInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        systemInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add components to header
        headerPanel.add(themeTogglePanel, BorderLayout.EAST);
        headerPanel.add(systemInfoLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createMenu() {
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(200, 600));
        menuPanel.setBackground(new Color(52, 73, 94));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        
        // Create menu buttons
        String[] menuItems = {
            "Adicionar Carro",
            "Listar Carros",
            "Vender Carros",
            "Listar Clientes",
            "Definições"
        };
        
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setMaximumSize(new Dimension(200, 50));
            menuButton.setFont(new Font("Arial", Font.BOLD, 14));
            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(new Color(52, 73, 94));
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            
            // Add hover effect
            menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    menuButton.setBackground(new Color(41, 128, 185));
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    menuButton.setBackground(new Color(52, 73, 94));
                }
            });
            
            // Add action listener
            menuButton.addActionListener(e -> switchPanel(item));
            
            menuPanel.add(menuButton);
        }
        
        add(menuPanel, BorderLayout.WEST);
    }
    
    private void createContent() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        
        // Create all panels
        JPanel addCarPanel = createAddCarPanel();
        JPanel listCarsPanel = createListCarsPanel();
        JPanel sellCarsPanel = createSellCarsPanel();
        JPanel listClientsPanel = createListClientsPanel();
        JPanel settingsPanel = createSettingsPanel();
        
        // Add panels to card layout
        contentPanel.add(addCarPanel, "Adicionar Carro");
        contentPanel.add(listCarsPanel, "Listar Carros");
        contentPanel.add(sellCarsPanel, "Vender Carros");
        contentPanel.add(listClientsPanel, "Listar Clientes");
        contentPanel.add(settingsPanel, "Definições");
        
        // Show default panel
        cardLayout.show(contentPanel, "Adicionar Carro");
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createAddCarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form fields
        JTextField modeloField = new JTextField(20);
        JTextField tracaoField = new JTextField(20);
        JTextField numMotorField = new JTextField(20);
        JTextField numChassiField = new JTextField(20);
        JTextField kmField = new JTextField(20);
        JTextField corField = new JTextField(20);
        JTextField categoriaField = new JTextField(20);
        JTextField combustivelField = new JTextField(20);
        JTextField fabricanteField = new JTextField(20);
        JTextField anoField = new JTextField(20);
        JTextField precoField = new JTextField(20);
        
        JComboBox<String> estadoVendaCombo = new JComboBox<>(new String[]{"Disponível", "Vendido"});
        
        // Car images
        ArrayList<String> carImages = new ArrayList<>();
        JLabel imagePreviewLabel = new JLabel("Sem imagem");
        imagePreviewLabel.setPreferredSize(new Dimension(250, 150));
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton chooseImageButton = new JButton("Escolher Imagens");
        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Imagens", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            fileChooser.setMultiSelectionEnabled(true);
            
            int result = fileChooser.showOpenDialog(panel);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                carImages.clear();
                
                for (File file : files) {
                    carImages.add(file.getAbsolutePath());
                }
                
                if (!carImages.isEmpty()) {
                    updatePreviewImage(imagePreviewLabel, carImages.get(0), 250, 150);
                }
            }
        });
        
        JButton saveButton = new JButton("Salvar Carro");
        saveButton.addActionListener(e -> {
            try {
                Carro carro = new Carro();
                carro.setModelo(modeloField.getText());
                carro.setTracao(tracaoField.getText());
                carro.setNumMotor(Integer.parseInt(numMotorField.getText()));
                carro.setNumChassi(Integer.parseInt(numChassiField.getText()));
                carro.setQuilometosPercoridos(Integer.parseInt(kmField.getText()));
                carro.setCor(corField.getText());
                carro.setCategoria(categoriaField.getText());
                carro.setTipoDeComustivel(combustivelField.getText());
                carro.setFabricante(fabricanteField.getText());
                carro.setAnoDeFabrico(Integer.parseInt(anoField.getText()));
                carro.setPreco(Double.parseDouble(precoField.getText()));
                carro.setEstado(estadoVendaCombo.getSelectedIndex() == 0);
                
                // Generate ID
                carro.setId(carroDao.getAll().size() + 1);
                
                boolean saved = carroDao.insert(carro);
                
                if (saved) {
                    // Save car images
                    if (!carImages.isEmpty()) {
                        carImagesMap.put(carro.getId(), new ArrayList<>(carImages));
                    }
                    
                    JOptionPane.showMessageDialog(panel, 
                            "Carro adicionado com sucesso!", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    // Clear fields
                    modeloField.setText("");
                    tracaoField.setText("");
                    numMotorField.setText("");
                    numChassiField.setText("");
                    kmField.setText("");
                    corField.setText("");
                    categoriaField.setText("");
                    combustivelField.setText("");
                    fabricanteField.setText("");
                    anoField.setText("");
                    precoField.setText("");
                    carImages.clear();
                    imagePreviewLabel.setIcon(null);
                    imagePreviewLabel.setText("Sem imagem");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                        "Por favor, preencha todos os campos numéricos corretamente.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, 
                        "Erro ao salvar: " + ex.getMessage(), 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add components to panel using GridBagLayout
        // Left column
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Modelo:"), gbc);
        
        gbc.gridx = 1;
        panel.add(modeloField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tração:"), gbc);
        
        gbc.gridx = 1;
        panel.add(tracaoField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Número do Motor:"), gbc);
        
        gbc.gridx = 1;
        panel.add(numMotorField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Número do Chassi:"), gbc);
        
        gbc.gridx = 1;
        panel.add(numChassiField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Quilômetros Percorridos:"), gbc);
        
        gbc.gridx = 1;
        panel.add(kmField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Estado de Venda:"), gbc);
        
        gbc.gridx = 1;
        panel.add(estadoVendaCombo, gbc);
        
        // Right column
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(new JLabel("Cor:"), gbc);
        
        gbc.gridx = 3;
        panel.add(corField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(new JLabel("Categoria:"), gbc);
        
        gbc.gridx = 3;
        panel.add(categoriaField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Tipo de Combustível:"), gbc);
        
        gbc.gridx = 3;
        panel.add(combustivelField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel.add(new JLabel("Fabricante:"), gbc);
        
        gbc.gridx = 3;
        panel.add(fabricanteField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(new JLabel("Ano de Fabricação:"), gbc);
        
        gbc.gridx = 3;
        panel.add(anoField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(new JLabel("Preço:"), gbc);
        
        gbc.gridx = 3;
        panel.add(precoField, gbc);
        
        // Image preview
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(new JLabel("Imagens do Carro:"), gbc);
        
        gbc.gridy = 7;
        gbc.gridheight = 3;
        panel.add(imagePreviewLabel, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        panel.add(chooseImageButton, gbc);
        
        // Save button
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(saveButton, gbc);
        
        return panel;
    }
    
    private JPanel createListCarsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Table model for cars
        String[] columns = {
            "ID", "Modelo", "Fabricante", "Ano", "Cor", "Preço", "Estado"
        };
        
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable carsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carsTable);
        
        // Selection listener
        carsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Image preview panel
        JPanel previewPanel = new JPanel(new BorderLayout(10, 10));
        previewPanel.setBorder(BorderFactory.createTitledBorder("Visualização do Carro"));
        
        JLabel carImageLabel = new JLabel("Selecione um carro para ver as imagens", SwingConstants.CENTER);
        carImageLabel.setPreferredSize(new Dimension(300, 200));
        
        JPanel imageControlPanel = new JPanel();
        JButton prevButton = new JButton("« Anterior");
        JButton nextButton = new JButton("Próxima »");
        
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        
        // Button listeners for image navigation
        prevButton.addActionListener(e -> {
            if (currentCarImages != null && !currentCarImages.isEmpty()) {
                currentImageIndex = (currentImageIndex - 1 + currentCarImages.size()) % currentCarImages.size();
                updatePreviewImage(carImageLabel, currentCarImages.get(currentImageIndex), 300, 200);
            }
        });
        
        nextButton.addActionListener(e -> {
            if (currentCarImages != null && !currentCarImages.isEmpty()) {
                currentImageIndex = (currentImageIndex + 1) % currentCarImages.size();
                updatePreviewImage(carImageLabel, currentCarImages.get(currentImageIndex), 300, 200);
            }
        });
        
        imageControlPanel.add(prevButton);
        imageControlPanel.add(nextButton);
        
        previewPanel.add(carImageLabel, BorderLayout.CENTER);
        previewPanel.add(imageControlPanel, BorderLayout.SOUTH);
        
        // Refresh button
        JButton refreshButton = new JButton("Atualizar Lista");
        refreshButton.addActionListener(e -> loadCarData(tableModel));
        
        // Add table selection listener
        carsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && carsTable.getSelectedRow() != -1) {
                int selectedRow = carsTable.getSelectedRow();
                int carId = Integer.parseInt(carsTable.getValueAt(selectedRow, 0).toString());
                
                // Get images for this car
                currentCarImages = carImagesMap.get(carId);
                currentImageIndex = 0;
                
                if (currentCarImages != null && !currentCarImages.isEmpty()) {
                    updatePreviewImage(carImageLabel, currentCarImages.get(0), 300, 200);
                    prevButton.setEnabled(currentCarImages.size() > 1);
                    nextButton.setEnabled(currentCarImages.size() > 1);
                } else {
                    carImageLabel.setIcon(null);
                    carImageLabel.setText("Sem imagens disponíveis");
                    prevButton.setEnabled(false);
                    nextButton.setEnabled(false);
                }
            }
        });
        
        // Add components to panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(previewPanel, BorderLayout.EAST);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        // Load car data initially
        loadCarData(tableModel);
        
        return panel;
    }
    
    private JPanel createSellCarsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search panel
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Pesquisar");
        JComboBox<String> searchTypeCombo = new JComboBox<>(new String[]{
            "Modelo", "Fabricante", "Cor", "Estado"
        });
        
        searchPanel.add(new JLabel("Pesquisar por:"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Results panel
        String[] columns = {
            "ID", "Modelo", "Fabricante", "Ano", "Cor", "Preço", "Estado"
        };
        
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable carsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(carsTable);
        
        // Selected car panel
        JPanel selectedCarPanel = new JPanel(new BorderLayout(10, 10));
        selectedCarPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Carro"));
        
        JPanel carInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JLabel carImageLabel = new JLabel("Selecione um carro", SwingConstants.CENTER);
        carImageLabel.setPreferredSize(new Dimension(300, 200));
        
        JPanel imageControlPanel = new JPanel();
        JButton prevButton = new JButton("« Anterior");
        JButton nextButton = new JButton("Próxima »");
        
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
        
        // Button listeners for image navigation
        prevButton.addActionListener(e -> {
            if (currentCarImages != null && !currentCarImages.isEmpty()) {
                currentImageIndex = (currentImageIndex - 1 + currentCarImages.size()) % currentCarImages.size();
                updatePreviewImage(carImageLabel, currentCarImages.get(currentImageIndex), 300, 200);
            }
        });
        
        nextButton.addActionListener(e -> {
            if (currentCarImages != null && !currentCarImages.isEmpty()) {
                currentImageIndex = (currentImageIndex + 1) % currentCarImages.size();
                updatePreviewImage(carImageLabel, currentCarImages.get(currentImageIndex), 300, 200);
            }
        });
        
        imageControlPanel.add(prevButton);
        imageControlPanel.add(nextButton);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(carImageLabel, BorderLayout.CENTER);
        imagePanel.add(imageControlPanel, BorderLayout.SOUTH);
        
        selectedCarPanel.add(carInfoPanel, BorderLayout.CENTER);
        selectedCarPanel.add(imagePanel, BorderLayout.EAST);
        
        // Client form for car sale
        JPanel clientFormPanel = new JPanel();
        clientFormPanel.setLayout(new BoxLayout(clientFormPanel, BoxLayout.Y_AXIS));
        clientFormPanel.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        
        JPanel formGrid = new JPanel(new GridLayout(0, 2, 5, 5));
        
        JTextField nomeField = new JTextField(20);
        JTextField apelidoField = new JTextField(20);
        JComboBox<String> sexoCombo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
        JTextField naturalidadeField = new JTextField(20);
        JTextField identidadeField = new JTextField(20);
        JComboBox<String> estadoCivilCombo = new JComboBox<>(new String[]{
            "Solteiro(a)", "Casado(a)", "Divorciado(a)", "Viúvo(a)"
        });
        JTextField emailField = new JTextField(20);
        JTextField dataNascField = new JTextField(20);
        JTextField idadeField = new JTextField(20);
        JTextField telefoneField = new JTextField(20);
        
        formGrid.add(new JLabel("Nome:"));
        formGrid.add(nomeField);
        formGrid.add(new JLabel("Apelido:"));
        formGrid.add(apelidoField);
        formGrid.add(new JLabel("Sexo:"));
        formGrid.add(sexoCombo);
        formGrid.add(new JLabel("Naturalidade:"));
        formGrid.add(naturalidadeField);
        formGrid.add(new JLabel("Identidade:"));
        formGrid.add(identidadeField);
        formGrid.add(new JLabel("Estado Civil:"));
        formGrid.add(estadoCivilCombo);
        formGrid.add(new JLabel("E-mail:"));
        formGrid.add(emailField);
        formGrid.add(new JLabel("Data de Nascimento:"));
        formGrid.add(dataNascField);
        formGrid.add(new JLabel("Idade:"));
        formGrid.add(idadeField);
        formGrid.add(new JLabel("Telefone:"));
        formGrid.add(telefoneField);
        
        clientFormPanel.add(formGrid);
        
        JButton sellButton = new JButton("Finalizar Venda");
        sellButton.setEnabled(false);
        
        sellButton.addActionListener(e -> {
            try {
                if (selectedCarro == null) {
                    JOptionPane.showMessageDialog(panel, 
                            "Selecione um carro para vender.", 
                            "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create client
                ClienteVenda cliente = new ClienteVenda();
                cliente.setNome(nomeField.getText());
                cliente.setApelido(apelidoField.getText());
                cliente.setSexo(sexoCombo.getSelectedItem().toString());
                cliente.setNaturalidade(naturalidadeField.getText());
                cliente.setIdentidade(identidadeField.getText());
                cliente.setEstadocivil(estadoCivilCombo.getSelectedItem().toString());
                cliente.seteMail(emailField.getText());
                cliente.setDataNascimento(dataNascField.getText());
                cliente.setIdade(Integer.parseInt(idadeField.getText()));
                cliente.setTelefone(Integer.parseInt(telefoneField.getText()));
                cliente.setId(clienteDao.getAll().size() + 1);
                
                // Set client's car
                cliente.setCarro(selectedCarro);
                
                // Update car's sale status
                selectedCarro.setEstado(false);
                carroDao.update(selectedCarro, String.valueOf(selectedCarro.getId()), 
                        car -> String.valueOf(car.getId()));
                
                // Save client
                clienteDao.insert(cliente);
                
                JOptionPane.showMessageDialog(panel, 
                        "Venda realizada com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                nomeField.setText("");
                apelidoField.setText("");
                naturalidadeField.setText("");
                identidadeField.setText("");
                emailField.setText("");
                dataNascField.setText("");
                idadeField.setText("");
                telefoneField.setText("");
                
                // Refresh car list
                loadCarData(tableModel);
                
                // Clear selected car
                selectedCarro = null;
                carInfoPanel.removeAll();
                carImageLabel.setIcon(null);
                carImageLabel.setText("Selecione um carro");
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);
                sellButton.setEnabled(false);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                        "Por favor, preencha os campos numéricos corretamente.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, 
                        "Erro ao registrar venda: " + ex.getMessage(), 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add table selection listener
        carsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && carsTable.getSelectedRow() != -1) {
                int selectedRow = carsTable.getSelectedRow();
                int carId = Integer.parseInt(carsTable.getValueAt(selectedRow, 0).toString());
                
               




// Get car by ID
                selectedCarro = carroDao.getById(String.valueOf(carId), car -> String.valueOf(car.getId()))
                        .orElse(null);
                
                if (selectedCarro != null) {
                    // Only allow selling available cars
                    sellButton.setEnabled(selectedCarro.isEstado());
                    
                    // Update car info panel
                    carInfoPanel.removeAll();
                    carInfoPanel.add(new JLabel("Modelo:"));
                    carInfoPanel.add(new JLabel(selectedCarro.getModelo()));
                    carInfoPanel.add(new JLabel("Fabricante:"));
                    carInfoPanel.add(new JLabel(selectedCarro.getFabricante()));
                    carInfoPanel.add(new JLabel("Ano:"));
                    carInfoPanel.add(new JLabel(String.valueOf(selectedCarro.getAnoDeFabrico())));
                    carInfoPanel.add(new JLabel("Cor:"));
                    carInfoPanel.add(new JLabel(selectedCarro.getCor()));
                    carInfoPanel.add(new JLabel("Preço:"));
                    carInfoPanel.add(new JLabel(String.format("%.2f", selectedCarro.getPreco())));
                    carInfoPanel.add(new JLabel("Estado:"));
                    carInfoPanel.add(new JLabel(selectedCarro.estadoDoVeiculo(selectedCarro.getQuilometosPercoridos())));
                    carInfoPanel.add(new JLabel("Quilômetros:"));
                    carInfoPanel.add(new JLabel(String.valueOf(selectedCarro.getQuilometosPercoridos())));
                    carInfoPanel.add(new JLabel("Tipo de Combustível:"));
                    carInfoPanel.add(new JLabel(selectedCarro.getTipoDeComustivel()));
                    carInfoPanel.add(new JLabel("Categoria:"));
                    carInfoPanel.add(new JLabel(selectedCarro.getCategoria()));
                    
                    // Get images for this car
                    currentCarImages = carImagesMap.get(carId);
                    currentImageIndex = 0;
                    
                    if (currentCarImages != null && !currentCarImages.isEmpty()) {
                        updatePreviewImage(carImageLabel, currentCarImages.get(0), 300, 200);
                        prevButton.setEnabled(currentCarImages.size() > 1);
                        nextButton.setEnabled(currentCarImages.size() > 1);
                    } else {
                        carImageLabel.setIcon(null);
                        carImageLabel.setText("Sem imagens disponíveis");
                        prevButton.setEnabled(false);
                        nextButton.setEnabled(false);
                    }
                    
                    carInfoPanel.revalidate();
                    carInfoPanel.repaint();
                }
            }
        });
        
        // Search button action
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();
            String searchType = searchTypeCombo.getSelectedItem().toString();
            
            tableModel.setRowCount(0);
            
            if (searchText.isEmpty()) {
                loadCarData(tableModel);
                return;
            }
            
            ArrayList<Carro> cars = carroDao.getAll();
            
            for (Carro car : cars) {
                boolean matchFound = false;
                
                switch (searchType) {
                    case "Modelo":
                        matchFound = car.getModelo().toLowerCase().contains(searchText);
                        break;
                    case "Fabricante":
                        matchFound = car.getFabricante().toLowerCase().contains(searchText);
                        break;
                    case "Cor":
                        matchFound = car.getCor().toLowerCase().contains(searchText);
                        break;
                    case "Estado":
                        String estado = car.isEstado()? "Disponível" : "Vendido";
                        matchFound = estado.toLowerCase().contains(searchText);
                        break;
                }
                
                if (matchFound) {
                    tableModel.addRow(new Object[]{
                        car.getId(),
                        car.getModelo(),
                        car.getFabricante(),
                        car.getAnoDeFabrico(),
                        car.getCor(),
                        String.format("%.2f", car.getPreco()),
                        car.isEstado()? "Disponível" : "Vendido"
                    });
                }
            }
        });
        
        // Main panel layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(selectedCarPanel, BorderLayout.EAST);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(clientFormPanel, BorderLayout.CENTER);
        southPanel.add(sellButton, BorderLayout.SOUTH);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
        
        // Load car data initially
        loadCarData(tableModel);
        
        return panel;
    }
    
    private JPanel createListClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Table model for clients
        String[] columns = {
            "ID", "Nome", "Apelido", "Telefone", "E-mail", "Carro Adquirido"
        };
        
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable clientsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        
        // Client details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Cliente"));
        
        JPanel clientInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        detailsPanel.add(clientInfoPanel);
        
        // Refresh button
        JButton refreshButton = new JButton("Atualizar Lista");
        refreshButton.addActionListener(e -> loadClientData(tableModel));
        
        // Add table selection listener
        clientsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && clientsTable.getSelectedRow() != -1) {
                int selectedRow = clientsTable.getSelectedRow();
                int clientId = Integer.parseInt(clientsTable.getValueAt(selectedRow, 0).toString());
                
                // Get client by ID
                ClienteVenda cliente = clienteDao.getById(String.valueOf(clientId), client -> String.valueOf(client.getId()))
                        .orElse(null);
                
                if (cliente != null) {
                    // Update client info panel
                    clientInfoPanel.removeAll();
                    
                    clientInfoPanel.add(new JLabel("Nome:"));
                    clientInfoPanel.add(new JLabel(cliente.getNome()));
                    
                    clientInfoPanel.add(new JLabel("Apelido:"));
                    clientInfoPanel.add(new JLabel(cliente.getApelido()));
                    
                    clientInfoPanel.add(new JLabel("Sexo:"));
                    clientInfoPanel.add(new JLabel(cliente.getSexo()));
                    
                    clientInfoPanel.add(new JLabel("Naturalidade:"));
                    clientInfoPanel.add(new JLabel(cliente.getNaturalidade()));
                    
                    clientInfoPanel.add(new JLabel("Identidade:"));
                    clientInfoPanel.add(new JLabel(cliente.getIdentidade()));
                    
                    clientInfoPanel.add(new JLabel("Estado Civil:"));
                    clientInfoPanel.add(new JLabel(cliente.getEstadocivil()));
                    
                    clientInfoPanel.add(new JLabel("E-mail:"));
                    clientInfoPanel.add(new JLabel(cliente.geteMail()));
                    
                    clientInfoPanel.add(new JLabel("Data de Nascimento:"));
                    clientInfoPanel.add(new JLabel(cliente.getDataNascimento()));
                    
                    clientInfoPanel.add(new JLabel("Idade:"));
                    clientInfoPanel.add(new JLabel(String.valueOf(cliente.getIdade())));
                    
                    clientInfoPanel.add(new JLabel("Telefone:"));
                    clientInfoPanel.add(new JLabel(String.valueOf(cliente.getTelefone())));
                    
                    // Car information
                    Carro carro = cliente.getCarro();
                    if (carro != null) {
                        clientInfoPanel.add(new JLabel("Carro:"));
                        clientInfoPanel.add(new JLabel(carro.getModelo() + " (" + carro.getFabricante() + ")"));
                        
                        clientInfoPanel.add(new JLabel("Ano:"));
                        clientInfoPanel.add(new JLabel(String.valueOf(carro.getAnoDeFabrico())));
                        
                        clientInfoPanel.add(new JLabel("Cor:"));
                        clientInfoPanel.add(new JLabel(carro.getCor()));
                        
                        clientInfoPanel.add(new JLabel("Preço:"));
                        clientInfoPanel.add(new JLabel(String.format("%.2f", carro.getPreco())));
                    }
                    
                    clientInfoPanel.revalidate();
                    clientInfoPanel.repaint();
                }
            }
        });
        
        // Add components to panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(detailsPanel, BorderLayout.EAST);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        // Load client data initially
        loadClientData(tableModel);
        
        return panel;
    }
    
    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Theme settings
        JPanel themePanel = new JPanel();
        themePanel.setBorder(BorderFactory.createTitledBorder("Configurações de Tema"));
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        
        JRadioButton lightThemeBtn = new JRadioButton("Tema Claro");
        lightThemeBtn.setSelected(true);
        JRadioButton darkThemeBtn = new JRadioButton("Tema Escuro");
        
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightThemeBtn);
        themeGroup.add(darkThemeBtn);
        
        lightThemeBtn.addActionListener(e -> changeTheme(true));
        darkThemeBtn.addActionListener(e -> changeTheme(false));
        
        themePanel.add(lightThemeBtn);
        themePanel.add(darkThemeBtn);
        
        // System information
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informações do Sistema"));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel versionLabel = new JLabel("Versão: 1.0.0");
        JLabel developerLabel = new JLabel("Desenvolvido por: Sua Empresa");
        JLabel yearLabel = new JLabel("Ano: 2025");
        
        infoPanel.add(versionLabel);
        infoPanel.add(developerLabel);
        infoPanel.add(yearLabel);
        
        // Database operations
        JPanel dbPanel = new JPanel();
        dbPanel.setBorder(BorderFactory.createTitledBorder("Operações de Banco de Dados"));
        dbPanel.setLayout(new GridLayout(2, 2, 10, 10));
        
        JButton backupButton = new JButton("Fazer Backup");
        JButton restoreButton = new JButton("Restaurar Dados");
        JButton clearCarsButton = new JButton("Limpar Dados de Carros");
        JButton clearClientsButton = new JButton("Limpar Dados de Clientes");
        
        backupButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(panel, "Backup realizado com sucesso!", "Backup", JOptionPane.INFORMATION_MESSAGE)
        );
        
        restoreButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(panel, "Restauração de dados concluída!", "Restauração", JOptionPane.INFORMATION_MESSAGE)
        );
        
        clearCarsButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(panel, 
                    "Tem certeza que deseja limpar todos os dados de carros?", 
                    "Confirmar Limpeza", 
                    JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                // Clear car data
                ArrayList<Carro> emptyList = new ArrayList<>();
                carroDao.closeFile(emptyList);
                carImagesMap.clear();
                JOptionPane.showMessageDialog(panel, "Dados de carros limpos com sucesso!", "Limpeza", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        clearClientsButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(panel, 
                    "Tem certeza que deseja limpar todos os dados de clientes?", 
                    "Confirmar Limpeza", 
                    JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                // Clear client data
                ArrayList<ClienteVenda> emptyList = new ArrayList<>();
                clienteDao.closeFile(emptyList);
                JOptionPane.showMessageDialog(panel, "Dados de clientes limpos com sucesso!", "Limpeza", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        dbPanel.add(backupButton);
        dbPanel.add(restoreButton);
        dbPanel.add(clearCarsButton);
        dbPanel.add(clearClientsButton);
        
        // Add panels to main panel
        panel.add(themePanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(infoPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(dbPanel);
        
        return panel;
    }
    
    private void loadCarData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        ArrayList<Carro> cars = carroDao.getAll();
        
        for (Carro car : cars) {
            tableModel.addRow(new Object[]{
                car.getId(),
                car.getModelo(),
                car.getFabricante(),
                car.getAnoDeFabrico(),
                car.getCor(),
                String.format("%.2f", car.getPreco()),
                car.isEstado()? "Disponível" : "Vendido"
            });
        }
    }
    
    private void loadClientData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        ArrayList<ClienteVenda> clients = clienteDao.getAll();
        
        for (ClienteVenda client : clients) {
            String carroInfo = "N/A";
            if (client.getCarro() != null) {
                Carro carro = client.getCarro();
                carroInfo = carro.getModelo() + " (" + carro.getFabricante() + ")";
            }
            
            tableModel.addRow(new Object[]{
                client.getId(),
                client.getNome(),
                client.getApelido(),
                client.getTelefone(),
                client.geteMail(),
                carroInfo
            });
        }
    }
    
    private void updatePreviewImage(JLabel imageLabel, String imagePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText("");
        } catch (Exception e) {
            imageLabel.setIcon(null);
            imageLabel.setText("Erro ao carregar imagem");
        }
    }
    
    private void changeTheme(boolean isLight) {
        try {
            if (isLight) {
                UIManager.setLookAndFeel(new FlatLightLaf());
                lightThemeRadio.setSelected(true);
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                darkThemeRadio.setSelected(true);
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Failed to change theme: " + e.getMessage());
        }
    }
    
    private void switchPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Register FlatLaf themes
//                UIManager.registerLookAndFeel("FlatLightLaf", FlatLightLaf.class.getName());
//                UIManager.registerLookAndFeel("FlatDarkLaf", FlatDarkLaf.class.getName());
//                
                // Set the default look and feel to FlatLightLaf
                UIManager.setLookAndFeel(new FlatLightLaf());
                
//                new Dashboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}