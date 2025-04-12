/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;




import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class AdminFrame extends JFrame {
    
    // Componentes principais da interface
    private JPanel contentPanel;
    private JPanel headerPanel;
    private JPanel sidebarPanel;
    private JRadioButton lightThemeRadio;
    private JRadioButton darkThemeRadio;
    private JLabel systemInfoLabel;
    
    // Cores para os temas (serão sobrescritas pelo FlatLaf)
    private boolean isDarkTheme = false;
    
    public AdminFrame() {
        setTitle("Sistema de Gestão de Venda de Carros - Painel de Administrador");
        setSize(1200, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Layout principal
        setLayout(new BorderLayout());
        
        // Inicializa os componentes
        initComponents();
        
        // Aplica o tema inicial (FlatMacLightLaf por padrão)
        applyTheme();
        
        setVisible(true);
    }
    
    private void initComponents() {
        // Painel de cabeçalho
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(1096, 45));
        headerPanel.setLayout(new BorderLayout());
        
        // Painel para controles de tema
        JPanel themePanel = new JPanel();
        themePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Radio buttons para tema
        lightThemeRadio = new JRadioButton("Tema Claro");
        lightThemeRadio.setSelected(true);
        darkThemeRadio = new JRadioButton("Tema Escuro");
        
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightThemeRadio);
        themeGroup.add(darkThemeRadio);
        
        // Adiciona listeners para os radio buttons
        lightThemeRadio.addActionListener(e -> {
            isDarkTheme = false;
            applyTheme();
        });
        
        darkThemeRadio.addActionListener(e -> {
            isDarkTheme = true;
            applyTheme();
        });
        
        themePanel.add(lightThemeRadio);
        themePanel.add(darkThemeRadio);
        
        // Informações do sistema
        systemInfoLabel = new JLabel("Sistema de Gestão de Carros v1.0 | Usuário: Admin");
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.add(systemInfoLabel);
        
        headerPanel.add(themePanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.EAST);
        
        // Painel lateral (menu)
        sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, 635));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Botões do menu
        String[] menuOptions = {
            "Adicionar Funcionário", 
            "Listar Funcionários", 
            "Gerir Taxas", 
            "Relatórios", 
            "Definições"
        };
        
        for (String option : menuOptions) {
            JButton menuButton = createMenuButton(option);
            sidebarPanel.add(menuButton);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // Painel de conteúdo (central)
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BorderLayout());
        
        // Adiciona um painel de boas-vindas por padrão
        showWelcomePanel();
        
        // Adiciona todos os componentes ao frame
        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        
        // Adiciona ação ao botão
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpa o painel de conteúdo
                contentPanel.removeAll();
                
                // Carrega o painel correspondente
                switch(text) {
                    case "Adicionar Funcionário":
                        showAddEmployeePanel();
                        break;
                    case "Listar Funcionários":
                        showEmployeeListPanel();
                        break;
                    case "Gerir Taxas":
                        showRatesPanel();
                        break;
                    case "Relatórios":
                        showReportsPanel();
                        break;
                    case "Definições":
                        showSettingsPanel();
                        break;
                }
                
                // Atualiza a interface
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        
        return button;
    }
    
    // Aplica o tema selecionado usando FlatLaf
    private void applyTheme() {
        try {
            if (isDarkTheme) {
                // Aplica o tema escuro do FlatLaf
                FlatDarkLaf.setup();
            } else {
                // Aplica o tema FlatMacLightLaf conforme solicitado
                FlatMacLightLaf.setup();
            }
            
            // Atualiza a UI para refletir o novo tema
            SwingUtilities.updateComponentTreeUI(this);
            
            // Ajustes específicos para o tema
            if (isDarkTheme) {
                // Ajustes específicos para o tema escuro
                for (Component c : sidebarPanel.getComponents()) {
                    if (c instanceof JButton) {
                        ((JButton) c).setOpaque(true);
                    }
                }
            } else {
                // Ajustes específicos para o tema claro
                for (Component c : sidebarPanel.getComponents()) {
                    if (c instanceof JButton) {
                        ((JButton) c).setOpaque(false);
                    }
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Painel de boas-vindas
    private void showWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Gestão de Venda de Carros");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subLabel = new JLabel("Selecione uma opção no menu lateral para começar");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
        
        statsPanel.add(createStatCard("Vendas do Mês", "34", new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Funcionários", "12", new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Carros em Stock", "45", new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Receita Mensal", "€78.500", new Color(243, 156, 18)));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(welcomeLabel);
        textPanel.add(subLabel);
        
        welcomePanel.add(textPanel, BorderLayout.NORTH);
        welcomePanel.add(statsPanel, BorderLayout.CENTER);
        
        contentPanel.add(welcomePanel);
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(color, 2));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Implementação dos painéis para as opções do menu
    private void showAddEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Adicionar Novo Funcionário");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(new JTextField());
        
        formPanel.add(new JLabel("Email:"));
        formPanel.add(new JTextField());
        
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(new JTextField());
        
        formPanel.add(new JLabel("Cargo:"));
        String[] cargos = {"Vendedor", "Gerente", "Administrativo", "Mecânico"};
        formPanel.add(new JComboBox<>(cargos));
        
        formPanel.add(new JLabel("Data de Contratação:"));
        formPanel.add(new JTextField("DD/MM/AAAA"));
        
        formPanel.add(new JLabel("Salário Base:"));
        formPanel.add(new JTextField());
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");
        
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        contentPanel.add(panel);
    }
    
    private void showEmployeeListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Lista de Funcionários");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Dados fictícios para a tabela
        String[] colunas = {"ID", "Nome", "Email", "Telefone", "Cargo", "Data Contratação"};
        Object[][] dados = {
            {"1", "João Silva", "joao.silva@email.com", "912345678", "Vendedor", "12/01/2023"},
            {"2", "Maria Santos", "maria.santos@email.com", "923456789", "Gerente", "05/06/2022"},
            {"3", "Pedro Oliveira", "pedro.oliveira@email.com", "934567890", "Administrativo", "20/03/2023"},
            {"4", "Ana Ferreira", "ana.ferreira@email.com", "945678901", "Vendedor", "15/07/2023"},
            {"5", "Carlos Rodrigues", "carlos.rodrigues@email.com", "956789012", "Mecânico", "30/09/2022"}
        };
        
        JTable table = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Pesquisar:"));
        searchPanel.add(new JTextField(20));
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(searchButton);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Remover");
        
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        contentPanel.add(panel);
    }
    
    private void showRatesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Gestão de Taxas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel ratesPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        
        ratesPanel.add(new JLabel("Taxa de Comissão (Vendedor):"));
        JPanel commissionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField commissionField = new JTextField(5);
        commissionField.setText("2.5");
        commissionPanel.add(commissionField);
        commissionPanel.add(new JLabel("%"));
        ratesPanel.add(commissionPanel);
        
        ratesPanel.add(new JLabel("Taxa de IVA:"));
        JPanel vatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField vatField = new JTextField(5);
        vatField.setText("23");
        vatPanel.add(vatField);
        vatPanel.add(new JLabel("%"));
        ratesPanel.add(vatPanel);
        
        ratesPanel.add(new JLabel("Taxa de Financiamento:"));
        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField financeField = new JTextField(5);
        financeField.setText("5.75");
        financePanel.add(financeField);
        financePanel.add(new JLabel("%"));
        ratesPanel.add(financePanel);
        
        ratesPanel.add(new JLabel("Taxa de Serviço:"));
        JPanel servicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField serviceField = new JTextField(5);
        serviceField.setText("1.5");
        servicePanel.add(serviceField);
        servicePanel.add(new JLabel("%"));
        ratesPanel.add(servicePanel);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Salvar Alterações");
        JButton resetButton = new JButton("Restaurar Padrões");
        
        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(ratesPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        contentPanel.add(panel);
    }
    
    private void showReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Relatórios");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JPanel reportsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        
        // Botões para diferentes tipos de relatórios
        JButton salesButton = new JButton("Relatório de Vendas");
        salesButton.setPreferredSize(new Dimension(200, 100));
        
        JButton inventoryButton = new JButton("Relatório de Inventário");
        inventoryButton.setPreferredSize(new Dimension(200, 100));
        
        JButton employeeButton = new JButton("Relatório de Funcionários");
        employeeButton.setPreferredSize(new Dimension(200, 100));
        
        JButton financialButton = new JButton("Relatório Financeiro");
        financialButton.setPreferredSize(new Dimension(200, 100));
        
        reportsPanel.add(salesButton);
        reportsPanel.add(inventoryButton);
        reportsPanel.add(employeeButton);
        reportsPanel.add(financialButton);
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Período:"));
        String[] periods = {"Hoje", "Esta Semana", "Este Mês", "Este Ano", "Personalizado"};
        JComboBox<String> periodCombo = new JComboBox<>(periods);
        filterPanel.add(periodCombo);
        
        filterPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        filterPanel.add(new JLabel("De:"));
        JTextField fromDate = new JTextField(10);
        filterPanel.add(fromDate);
        
        filterPanel.add(new JLabel("Até:"));
        JTextField toDate = new JTextField(10);
        filterPanel.add(toDate);
        
        JButton filterButton = new JButton("Filtrar");
        filterPanel.add(filterButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(filterPanel, BorderLayout.SOUTH);
        panel.add(reportsPanel, BorderLayout.CENTER);
        
        contentPanel.add(panel);
    }
    
    private void showSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Definições");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Painel de Configurações Gerais
        JPanel generalPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        generalPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        generalPanel.add(new JLabel("Nome da Empresa:"));
        generalPanel.add(new JTextField("Auto Motors"));
        
        generalPanel.add(new JLabel("Moeda:"));
        String[] currencies = {"Euro (€)", "Dólar ($)", "Libra (£)"};
        generalPanel.add(new JComboBox<>(currencies));
        
        generalPanel.add(new JLabel("Idioma:"));
        String[] languages = {"Português", "Inglês", "Espanhol", "Francês"};
        generalPanel.add(new JComboBox<>(languages));
        
        generalPanel.add(new JLabel("Formato de Data:"));
        String[] dateFormats = {"DD/MM/AAAA", "MM/DD/AAAA", "AAAA-MM-DD"};
        generalPanel.add(new JComboBox<>(dateFormats));
        
        generalPanel.add(new JLabel("Email para Notificações:"));
        generalPanel.add(new JTextField("admin@automotors.com"));
        
        // Painel de Backup
        JPanel backupPanel = new JPanel(new BorderLayout());
        backupPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel backupOptionsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        backupOptionsPanel.add(new JLabel("Backup Automático:"));
        JCheckBox autoBackupCheck = new JCheckBox();
        autoBackupCheck.setSelected(true);
        backupOptionsPanel.add(autoBackupCheck);
        
        backupOptionsPanel.add(new JLabel("Frequência:"));
        String[] frequencies = {"Diário", "Semanal", "Mensal"};
        backupOptionsPanel.add(new JComboBox<>(frequencies));
        
        backupOptionsPanel.add(new JLabel("Pasta de Backup:"));
        JPanel folderPanel = new JPanel(new BorderLayout());
        folderPanel.add(new JTextField("/var/backups/carmanager"), BorderLayout.CENTER);
        JButton browseButton = new JButton("...");
        folderPanel.add(browseButton, BorderLayout.EAST);
        backupOptionsPanel.add(folderPanel);
        
        JPanel backupButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backupNowButton = new JButton("Fazer Backup Agora");
        JButton restoreButton = new JButton("Restaurar Backup");
        backupButtonsPanel.add(backupNowButton);
        backupButtonsPanel.add(restoreButton);
        
        backupPanel.add(backupOptionsPanel, BorderLayout.NORTH);
        backupPanel.add(backupButtonsPanel, BorderLayout.SOUTH);
        
        // Painel de Usuários
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Dados fictícios para a tabela de usuários
        String[] userColumns = {"ID", "Nome", "Email", "Tipo", "Último Acesso"};
        Object[][] userData = {
            {"1", "Admin", "admin@automotors.com", "Administrador", "12/04/2025 09:15"},
            {"2", "João Silva", "joao.silva@email.com", "Vendedor", "11/04/2025 16:30"},
            {"3", "Maria Santos", "maria.santos@email.com", "Gerente", "12/04/2025 08:45"}
        };
        
        JTable userTable = new JTable(userData, userColumns);
        JScrollPane userScrollPane = new JScrollPane(userTable);
        
        JPanel userButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addUserButton = new JButton("Adicionar Usuário");
        JButton editUserButton = new JButton("Editar");
        JButton deleteUserButton = new JButton("Remover");
        
        userButtonsPanel.add(addUserButton);
        userButtonsPanel.add(editUserButton);
        userButtonsPanel.add(deleteUserButton);
        
        usersPanel.add(userScrollPane, BorderLayout.CENTER);
        usersPanel.add(userButtonsPanel, BorderLayout.SOUTH);
        
        // Adiciona as abas
        tabbedPane.addTab("Geral", generalPanel);
        tabbedPane.addTab("Backup", backupPanel);
        tabbedPane.addTab("Usuários", usersPanel);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Salvar Alterações");
        JButton cancelButton = new JButton("Cancelar");
        
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        contentPanel.add(panel);
    }
    
    public static void main(String[] args) {
        // Configura o FlatLaf como look and feel padrão
        try {
            // Configura o FlatMacLightLaf como tema padrão
            FlatMacLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Inicia a aplicação
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminFrame();
            }
        });
    }
}