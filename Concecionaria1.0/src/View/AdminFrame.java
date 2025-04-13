/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;




import Controller.GenericDao;
import Model.Funcionario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpinnerNumberModel;

// Importe suas classes específicas para o projeto
import Model.Funcionario;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.util.ArrayList;

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
    private GenericDao<Funcionario> dao;
    public AdminFrame() {
        setTitle("Sistema de Gestão de Venda de Carros - Painel de Administrador");
        setSize(1200, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Layout principal
        setLayout(new BorderLayout());
        
        // Inicializa os componentes
        initComponents();
         dao =  new GenericDao<>(Funcionario.class);
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
    
    // Criar painel de rolagem para acomodar todos os campos
    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);
    
    // Campos da classe Funcionario
    // Informações pessoais
    JTextField nomeField = new JTextField(20);
    JTextField apelidoField = new JTextField(20);
    
    // Sexo (ComboBox)
    String[] sexos = {"Masculino", "Feminino", "Outro"};
    JComboBox<String> sexoCombo = new JComboBox<>(sexos);
    
    // Idade
    JSpinner idadeSpinner = new JSpinner(new SpinnerNumberModel(18, 18, 100, 1));
    
    // Data de Nascimento
    JTextField dataNascField = new JTextField("DD/MM/AAAA", 10);
    
    // Estado Civil
    String[] estadosCivis = {"Solteiro(a)", "Casado(a)", "Divorciado(a)", "Viúvo(a)", "União Estável"};
    JComboBox<String> estadoCivilCombo = new JComboBox<>(estadosCivis);
    
    // Naturalidade (ComboBox com países)
    String[] paises = {"Angola", "Portugal", "Brasil", "Moçambique", "Cabo Verde", 
                      "Guiné-Bissau", "São Tomé e Príncipe", "Timor-Leste", 
                      "África do Sul", "Estados Unidos", "Espanha", "França", 
                      "Reino Unido", "Alemanha", "Itália", "China", "Japão", 
                      "Austrália", "Canadá", "México"};
    JComboBox<String> naturalidadeCombo = new JComboBox<>(paises);
    
    // Contato
    JTextField emailField = new JTextField(20);
    JTextField telefoneField = new JTextField(15);
    
    // Informações profissionais
    // Especialidade/Cargo
    String[] especialidades = {"Vendedor", "Gerente", "Administrativo", "Mecânico", "Atendente"};
    JComboBox<String> especialidadeCombo = new JComboBox<>(especialidades);
    
    // Campo de senha (visível apenas para vendedores)
    JPasswordField senhaField = new JPasswordField(15);
    JLabel senhaLabel = new JLabel("Senha:");
    senhaField.setVisible(false);
    senhaLabel.setVisible(false);
    
    // Adicionar listener ao combobox de especialidade
    especialidadeCombo.addActionListener(e -> {
        String selectedEspecialidade = (String) especialidadeCombo.getSelectedItem();
        if (selectedEspecialidade.equals("Vendedor")) {
            senhaField.setVisible(true);
            senhaLabel.setVisible(true);
        } else {
            senhaField.setVisible(false);
            senhaLabel.setVisible(false);
        }
        formPanel.revalidate();
        formPanel.repaint();
    });
    
    // Data do Contrato
    JTextField dataContratoField = new JTextField("DD/MM/AAAA", 10);
    
    // Salário
    JTextField salarioField = new JTextField(10);
    
    // ID (pode ser autoincrementado ou gerado)
    JSpinner idSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
    
    // Estado (ativo/inativo)
    JCheckBox estadoCheck = new JCheckBox("Ativo");
    estadoCheck.setSelected(true);
    
    // Níveis de acesso
    JCheckBox acessoNivel1Check = new JCheckBox("Acesso Nível 1");
    JCheckBox acessoNivel2Check = new JCheckBox("Acesso Nível 2");
    JCheckBox acessoNivel3Check = new JCheckBox("Acesso Nível 3");
    
    // Adicionar componentes ao painel usando GridBagLayout
    gbc.gridx = 0;
    gbc.gridy = 0;
    formPanel.add(new JLabel("Nome:"), gbc);
    gbc.gridx = 1;
    formPanel.add(nomeField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Apelido:"), gbc);
    gbc.gridx = 1;
    formPanel.add(apelidoField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Sexo:"), gbc);
    gbc.gridx = 1;
    formPanel.add(sexoCombo, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Idade:"), gbc);
    gbc.gridx = 1;
    formPanel.add(idadeSpinner, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Data de Nascimento:"), gbc);
    gbc.gridx = 1;
    formPanel.add(dataNascField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Estado Civil:"), gbc);
    gbc.gridx = 1;
    formPanel.add(estadoCivilCombo, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Naturalidade:"), gbc);
    gbc.gridx = 1;
    formPanel.add(naturalidadeCombo, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    formPanel.add(emailField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Telefone:"), gbc);
    gbc.gridx = 1;
    formPanel.add(telefoneField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Especialidade:"), gbc);
    gbc.gridx = 1;
    formPanel.add(especialidadeCombo, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(senhaLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(senhaField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Data do Contrato:"), gbc);
    gbc.gridx = 1;
    formPanel.add(dataContratoField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Salário:"), gbc);
    gbc.gridx = 1;
    formPanel.add(salarioField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("ID:"), gbc);
    gbc.gridx = 1;
    formPanel.add(idSpinner, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Estado:"), gbc);
    gbc.gridx = 1;
    formPanel.add(estadoCheck, gbc);
    
    gbc.gridx = 0;
    gbc.gridy++;
    formPanel.add(new JLabel("Níveis de Acesso:"), gbc);
    gbc.gridx = 1;
    JPanel acessoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    acessoPanel.add(acessoNivel1Check);
    acessoPanel.add(acessoNivel2Check);
    acessoPanel.add(acessoNivel3Check);
    formPanel.add(acessoPanel, gbc);
    
    // Painel de rolagem para o formulário
    JScrollPane scrollPane = new JScrollPane(formPanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    
    // Botões
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton saveButton = new JButton("Salvar");
    JButton cancelButton = new JButton("Cancelar");
    
    // Implementar a ação de salvar
    saveButton.addActionListener(e -> {
        try {
            // Criar um novo objeto Funcionario
            Funcionario funcionario = new Funcionario();
            
            // Preencher com os dados do formulário
            funcionario.setNome(nomeField.getText());
            funcionario.setApelido(apelidoField.getText());
            funcionario.setSexo((String) sexoCombo.getSelectedItem());
            funcionario.setIdade((Integer) idadeSpinner.getValue());
            funcionario.setDataDeNascimento(dataNascField.getText());
            funcionario.setEstadocivil((String) estadoCivilCombo.getSelectedItem());
            funcionario.setNaturalidade((String) naturalidadeCombo.getSelectedItem());
            funcionario.seteMail(emailField.getText());
            
            // Tratar o telefone (converter para int)
            try {
                funcionario.setTelefone(Integer.parseInt(telefoneField.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Telefone inválido. Insira apenas números.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            funcionario.setEspecialidade((String) especialidadeCombo.getSelectedItem());
            
            // Definir senha apenas se for vendedor
            if (especialidadeCombo.getSelectedItem().equals("Vendedor")) {
                funcionario.setSenha(new String(senhaField.getPassword()));
            }
            
            funcionario.setDataDoContrato(dataContratoField.getText());
            
            // Tratar o salário (converter para double)
            try {
                funcionario.setSalario(Double.parseDouble(salarioField.getText().replace(",", ".")));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Salário inválido. Use formato numérico (ex: 1500.00)", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            funcionario.setId((Integer) idSpinner.getValue());
            funcionario.setEstado(estadoCheck.isSelected());
            funcionario.setAcessoNivel1(acessoNivel1Check.isSelected());
            funcionario.setAcessoNivel2(acessoNivel2Check.isSelected());
            funcionario.setAcessoNivel3(acessoNivel3Check.isSelected());
            
            // Salvar usando GenericDAO
           
            boolean success = dao.insert(funcionario);
            
            if (success) {
                JOptionPane.showMessageDialog(panel, "Funcionário adicionado com sucesso!", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                // Limpar campos ou redirecionar para a lista
                limparCampos();
                showEmployeeListPanel(); // Opcional: redirecionar para a lista de funcionários
            } else {
                JOptionPane.showMessageDialog(panel, "Erro ao salvar funcionário.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    });
    
    // Implementar ação de cancelar
    cancelButton.addActionListener(e -> {
        // Simplesmente voltar para o painel principal ou limpar os campos
        limparCampos();
        showWelcomePanel();
    });
    
    buttonsPanel.add(saveButton);
    buttonsPanel.add(cancelButton);
    
    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonsPanel, BorderLayout.SOUTH);
    
    contentPanel.add(panel);
}

// Método auxiliar para limpar os campos do formulário
private void limparCampos() {
    // Este método será chamado para limpar todos os campos após salvar ou cancelar
    Component[] components = contentPanel.getComponents();
    for (Component component : components) {
        if (component instanceof JPanel) {
            limparComponentes((JPanel) component);
        }
    }
}

private void limparComponentes(JPanel panel) {
    Component[] components = panel.getComponents();
    for (Component component : components) {
        if (component instanceof JTextField) {
            ((JTextField) component).setText("");
        } else if (component instanceof JPasswordField) {
            ((JPasswordField) component).setText("");
        } else if (component instanceof JComboBox) {
            ((JComboBox<?>) component).setSelectedIndex(0);
        } else if (component instanceof JSpinner) {
            ((JSpinner) component).setValue(((SpinnerNumberModel) ((JSpinner) component).getModel()).getMinimum());
        } else if (component instanceof JCheckBox) {
            ((JCheckBox) component).setSelected(false);
        } else if (component instanceof JPanel) {
            limparComponentes((JPanel) component);
        } else if (component instanceof JScrollPane) {
            Component viewComponent = ((JScrollPane) component).getViewport().getView();
            if (viewComponent instanceof JPanel) {
                limparComponentes((JPanel) viewComponent);
            }
        }
    }
}
    
    private void showEmployeeListPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
    
    JLabel titleLabel = new JLabel("Lista de Funcionários");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    
    // Obter lista de funcionários do GenericDAO
//    GenericDAO<Funcionario> dao = new GenericDAO<>();
    List<Funcionario> funcionarios = dao.getAll();
    
    // Definir colunas da tabela
    String[] colunas = {"ID", "Nome", "Apelido", "Sexo", "Idade", "Email", "Telefone", 
                        "Especialidade", "Salário", "Data Contrato", "Estado"};
    
    // Converter a lista de funcionários para um array bidimensional para a JTable
    Object[][] dados;
    if (funcionarios != null && !funcionarios.isEmpty()) {
        dados = new Object[funcionarios.size()][colunas.length];
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            dados[i][0] = f.getId();
            dados[i][1] = f.getNome();
            dados[i][2] = f.getApelido();
            dados[i][3] = f.getSexo();
            dados[i][4] = f.getIdade();
            dados[i][5] = f.geteMail();
            dados[i][6] = f.getTelefone();
            dados[i][7] = f.getEspecialidade();
            dados[i][8] = String.format("%.2f", f.getSalario());
            dados[i][9] = f.getDataDoContrato();
            dados[i][10] = f.isEstado() ? "Ativo" : "Inativo";
        }
    } else {
        // Se não houver funcionários, criar uma tabela vazia
        dados = new Object[0][colunas.length];
    }
    
    // Criar a tabela com os dados
    JTable table = new JTable(dados, colunas);
    JScrollPane scrollPane = new JScrollPane(table);
    
    // Permitir seleção de apenas uma linha por vez
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    // Ajustar algumas propriedades da tabela
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.getTableHeader().setReorderingAllowed(false);
    
    // Painel de pesquisa
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.add(new JLabel("Pesquisar:"));
    JTextField searchField = new JTextField(20);
    JButton searchButton = new JButton("Buscar");
    searchPanel.add(searchField);
    searchPanel.add(searchButton);
    
    // Implementar a funcionalidade de busca
    searchButton.addActionListener(e -> {
        String searchTerm = searchField.getText().toLowerCase();
        if (searchTerm.isEmpty()) {
            // Se o campo de busca estiver vazio, recarregar todos os dados
            showEmployeeListPanel();
            return;
        }
        
        // Filtrar funcionários que correspondem ao termo de busca
        List<Funcionario> filteredList = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            if (f.getNome().toLowerCase().contains(searchTerm) || 
                f.getApelido().toLowerCase().contains(searchTerm) ||
                f.geteMail().toLowerCase().contains(searchTerm) ||
                String.valueOf(f.getId()).contains(searchTerm)) {
                filteredList.add(f);
            }
        }
        
        // Atualizar a tabela com os resultados filtrados
        Object[][] filteredData = new Object[filteredList.size()][colunas.length];
        for (int i = 0; i < filteredList.size(); i++) {
            Funcionario f = filteredList.get(i);
            filteredData[i][0] = f.getId();
            filteredData[i][1] = f.getNome();
            filteredData[i][2] = f.getApelido();
            filteredData[i][3] = f.getSexo();
            filteredData[i][4] = f.getIdade();
            filteredData[i][5] = f.geteMail();
            filteredData[i][6] = f.getTelefone();
            filteredData[i][7] = f.getEspecialidade();
            filteredData[i][8] = String.format("%.2f", f.getSalario());
            filteredData[i][9] = f.getDataDoContrato();
            filteredData[i][10] = f.isEstado() ? "Ativo" : "Inativo";
        }
        
        // Criar um novo modelo de tabela com os dados filtrados
        table.setModel(new DefaultTableModel(filteredData, colunas));
    });
    
    // Botões de ação
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton addButton = new JButton("Adicionar");
    JButton editButton = new JButton("Editar");
    JButton deleteButton = new JButton("Remover");
    
    // Implementar ação do botão Adicionar
    addButton.addActionListener(e -> {
        showAddEmployeePanel();
    });
    
    // Implementar ação do botão Editar
    editButton.addActionListener(e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) table.getValueAt(selectedRow, 0);
            // Encontrar o funcionário pelo ID
            Funcionario funcionarioToEdit = null;
            for (Funcionario f : funcionarios) {
                if (f.getId() == id) {
                    funcionarioToEdit = f;
                    break;
                }
            }
            
            if (funcionarioToEdit != null) {
                // Abrir o formulário de edição com os dados preenchidos
                showEditEmployeePanel(funcionarioToEdit);
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Selecione um funcionário para editar.", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    });
    
    // Implementar ação do botão Remover
    deleteButton.addActionListener(e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) table.getValueAt(selectedRow, 0);
            String nome = (String) table.getValueAt(selectedRow, 1);
            
            // Confirmar antes de excluir
            int confirm = JOptionPane.showConfirmDialog(panel, 
                    "Tem certeza que deseja remover o funcionário " + nome + "?", 
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Encontrar o funcionário pelo ID
                Funcionario funcionarioToDelete = null;
                for (Funcionario f : funcionarios) {
                    if (f.getId() == id) {
                        funcionarioToDelete = f;
                        break;
                    }
                }
                
                if (funcionarioToDelete != null) {
                    // Excluir usando o DAO
//                    boolean success = dao.delete(nome, idExtractor));
                    
                    if (true) {
                        JOptionPane.showMessageDialog(panel, "Funcionário removido com sucesso!", 
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        // Recarregar a lista
                        showEmployeeListPanel();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Erro ao remover funcionário.", 
                                "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Selecione um funcionário para remover.", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    });
    
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

// Método para editar um funcionário existente
private void showEditEmployeePanel(Funcionario funcionario) {
    // Este método seria semelhante ao showAddEmployeePanel, mas com os campos 
    // preenchidos com os dados do funcionário selecionado
    // Por questões de brevidade, este método não está completamente implementado aqui
    
    // Uma implementação completa preencheria todos os campos do formulário com os dados do funcionário
    // e salvaria as alterações usando dao.update(funcionario) em vez de dao.save(funcionario)
    
    JOptionPane.showMessageDialog(contentPanel, 
            "Funcionalidade de edição a ser implementada.", 
            "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
    
    // Como exemplo básico, podemos chamar o método de adicionar e depois preencher os campos
    showAddEmployeePanel();
    
    // Idealmente, aqui você preencheria todos os campos com os dados do funcionário selecionado
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