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
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CarSystemLogin extends JFrame {
    
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private JLabel carIcon;
    private JCheckBox rememberMe;
    private Timer animationTimer;
    private int carPosition = -100;
    private Color accentColor = new Color(30, 144, 255);
    Funcionario f ;
    JFrame frame ;
    boolean admin = true;
    public CarSystemLogin() {
        // Configuração básica da janela
        setTitle("Auto Vende - Sistema de Vendas de Veículos");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 850, 550, 20, 20));
        this.frame = this; 
        // Painel principal com layout personalizado
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);
        
        createComponents();
        setupAnimation();
        createLayout();
        
        // Tornando a janela arrastável
        makeDraggable();
    }
    
    private void createComponents() {
        // Painel de login com efeito de sombra
        loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);
                
                // Fundo do painel
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 15, 15);
                
                g2d.dispose();
            }
        };
        loginPanel.setOpaque(false);
        loginPanel.setBackground(new Color(50, 50, 55));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        
        // Logo e título
        JLabel titleLabel = new JLabel("Auto Vende");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestão de Vendas");
        subtitleLabel.setForeground(new Color(180, 180, 180));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Ícone de carro para animação
        carIcon = new JLabel(createCarIcon(accentColor, 60, 30));
        carIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Campos de entrada - Agora com ícones gerados programaticamente
        usernameField = createStyledTextField("Usuário", createUserIcon());
        passwordField = createStyledPasswordField("Senha", createLockIcon());
        
        // "Lembrar de mim" checkbox
        rememberMe = new JCheckBox("Lembrar de mim");
        rememberMe.setForeground(new Color(200, 200, 200));
        rememberMe.setOpaque(false);
        rememberMe.setFocusPainted(false);
        rememberMe.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Botão de login com efeito hover
        loginButton = new JButton("ENTRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(accentColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(accentColor.brighter());
                } else {
                    g2d.setColor(accentColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle textBounds = fm.getStringBounds(getText(), g2d).getBounds();
                
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), 
                    (getWidth() - textBounds.width) / 2,
                    (getHeight() - textBounds.height) / 2 + fm.getAscent());
                
                g2d.dispose();
            }
        };
        
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        loginButton.addActionListener(e -> performLogin());
        
        // Status label
        statusLabel = new JLabel("");
        statusLabel.setForeground(new Color(180, 180, 180));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botões de controle da janela
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        controlPanel.setOpaque(false);
        
        JButton minimizeButton = createControlButton("—", new Color(200, 200, 50));
        JButton closeButton = createControlButton("✕", new Color(240, 85, 85));
        
        minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));
        closeButton.addActionListener(e -> System.exit(0));
        
        controlPanel.add(minimizeButton);
        controlPanel.add(closeButton);
        
        mainPanel.add(controlPanel, BorderLayout.NORTH);
    }
    
    private void createLayout() {
        // Painel esquerdo com imagem de fundo
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradiente de fundo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 33, 44),
                    getWidth(), getHeight(), new Color(40, 55, 75)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Adicionar elementos gráficos
                g2d.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 40));
                
                // Círculos decorativos
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth() - 100, getHeight() - 100, 250, 250);
                
                // Linhas decorativas
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 60));
                
                for (int i = 0; i < 5; i++) {
                    int y = 100 + i * 90;
                    g2d.drawLine(20, y, getWidth() - 40, y - 30);
                }
                
                // Logo "Auto Vende" estilizado
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 28));
                g2d.drawString("AUTO VENDE", 50, 100);
                
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                g2d.setColor(new Color(200, 200, 200));
                g2d.drawString("Sistema Premium de", 50, 130);
                g2d.drawString("Gestão de Vendas de Veículos", 50, 155);
                
                // Desenhar um carro simples como silhueta
                int carX = 50 + carPosition;
                g2d.setColor(new Color(255, 255, 255, 150));
                
                // Corpo do carro
                g2d.fillRoundRect(carX, 350, 120, 40, 20, 20);
                g2d.fillRoundRect(carX + 20, 330, 70, 30, 10, 10);
                
                // Rodas
                g2d.fillOval(carX + 20, 380, 25, 25);
                g2d.fillOval(carX + 85, 380, 25, 25);
                
                g2d.dispose();
            }
        };
        
        // Montagem do painel de login
        loginPanel.add(carIcon);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(new Label("OLA") );
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(new JLabel("Hi there "));
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(rememberMe);
        loginPanel.add(Box.createVerticalStrut(25));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(statusLabel);
        
        // Layout principal
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.add(leftPanel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(loginPanel);
        
        // Ajustar os tamanhos relativos dos painéis
        mainPanel.add(contentWrapper, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        // Definir tamanhos preferidos
        leftPanel.setPreferredSize(new Dimension(450, 500));
        rightPanel.setPreferredSize(new Dimension(350, 500));
    }
    
    // Criar ícone de usuário programaticamente
    private ImageIcon createUserIcon() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Desenhar cabeça
        g2d.setColor(new Color(180, 180, 180));
        g2d.fillOval(4, 2, 8, 8);
        
        // Desenhar corpo
        g2d.fillOval(2, 11, 12, 7);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    // Criar ícone de cadeado programaticamente
    private ImageIcon createLockIcon() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Desenhar corpo do cadeado
        g2d.setColor(new Color(180, 180, 180));
        g2d.fillRoundRect(3, 7, 10, 8, 2, 2);
        
        // Desenhar arco do cadeado
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawArc(4, 2, 8, 8, 0, 180);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    private JTextField createStyledTextField(String placeholder, ImageIcon icon) {
        JTextField field = new JTextField(15) {
            private final String ph = placeholder;
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (getText().isEmpty()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2d.setColor(new Color(150, 150, 150));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    
                    int padding = (icon != null) ? 35 : 8;
                    g2d.drawString(ph, padding, getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2);
                    
                    g2d.dispose();
                }
                
                if (icon != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.drawImage(icon.getImage(), 8, (getHeight() - icon.getIconHeight()) / 2, null);
                    g2d.dispose();
                }
            }
        };
        
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(8, icon != null ? 35 : 8, 8, 8)
        ));
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setCaretColor(accentColor);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Efeito hover/focus
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor),
                    BorderFactory.createEmptyBorder(8, icon != null ? 35 : 8, 8, 8)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100)),
                    BorderFactory.createEmptyBorder(8, icon != null ? 35 : 8, 8, 8)
                ));
            }
        });
        
        return field;
    }
    
    private JPasswordField createStyledPasswordField(String placeholder, ImageIcon icon) {
        JPasswordField field = new JPasswordField(15) {
            private final String ph = placeholder;
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (getPassword().length == 0) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2d.setColor(new Color(150, 150, 150));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    
                    int padding = (icon != null) ? 35 : 8;
                    g2d.drawString(ph, padding, getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2);
                    
                    g2d.dispose();
                }
                
                if (icon != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.drawImage(icon.getImage(), 8, (getHeight() - icon.getIconHeight()) / 2, null);
                    g2d.dispose();
                }
            }
        };
        
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100)),
            BorderFactory.createEmptyBorder(8, icon != null ? 35 : 8, 8, 8)
        ));
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setCaretColor(accentColor);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setEchoChar('•');
        
        // Efeito hover/focus
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor),
                    BorderFactory.createEmptyBorder(8, icon != null ? 35 : 8, 8, 8)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100)),
                    BorderFactory.createEmptyBorder(8, icon != null ? 35 : 8, 8, 8)
                ));
            }
        });
        
        return field;
    }
    
    private JButton createControlButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color);
                } else {
                    g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 150));
                }
                
                g2d.fillOval(0, 0, getWidth(), getHeight());
                
                // Centralizar o texto
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle textBounds = fm.getStringBounds(getText(), g2d).getBounds();
                
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), 
                    (getWidth() - textBounds.width) / 2,
                    (getHeight() - textBounds.height) / 2 + fm.getAscent());
                
                g2d.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(20, 20));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private Icon createCarIcon(Color color, int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.translate(x, y);
                
                // Corpo do carro
                g2d.setColor(color);
                g2d.fillRoundRect(10, 15, width - 20, height / 2, 10, 10);
                g2d.fillRoundRect(20, 5, width - 40, height / 2, 8, 8);
                
                // Rodas
                g2d.setColor(new Color(50, 50, 50));
                g2d.fillOval(15, height - 15, 12, 12);
                g2d.fillOval(width - 27, height - 15, 12, 12);
                
                // Detalhes
                g2d.setColor(new Color(220, 220, 220));
                g2d.fillRect(width - 15, 18, 5, 5);  // Farol traseiro
                g2d.setColor(new Color(255, 255, 200));
                g2d.fillRect(10, 18, 5, 5);  // Farol dianteiro
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return width;
            }
            
            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }
    
    private void setupAnimation() {
        // Animação ao iniciar
        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            int frame = 0;
            
            @Override
            public void run() {
                // Animar o carro no painel esquerdo
                if (carPosition < 400) {
                    carPosition += 5;
                } else {
                    carPosition = -150;
                }
                
                // Efeito pulsante no ícone do carro
                if (frame % 20 == 0) {
                    SwingUtilities.invokeLater(() -> {
                        carIcon.setIcon(createCarIcon(
                            new Color(
                                accentColor.getRed() + (int)(Math.sin(frame * 0.1) * 20), 
                                accentColor.getGreen() + (int)(Math.sin(frame * 0.1) * 20),
                                accentColor.getBlue()
                            ), 
                            60, 30
                        ));
                    });
                }
                
                frame++;
                repaint();
            }
        }, 0, 50);
    }
    
    private void makeDraggable() {
        // Permitir que a janela seja arrastada
        Point clickPoint = new Point();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickPoint.x = e.getX();
                clickPoint.y = e.getY();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Obter localização atual da janela
                Point current = getLocation();
                
                // Mover a janela para a nova posição
                setLocation(current.x + e.getX() - clickPoint.x, 
                           current.y + e.getY() - clickPoint.y);
            }
        });
    }
    
    private void performLogin() {
        // Simular processo de login com animação
        loginButton.setEnabled(false);
        usernameField.setEnabled(false);
        passwordField.setEnabled(false);
        statusLabel.setText("Autenticando...");
        statusLabel.setForeground(new Color(180, 180, 180));
        
        // Usar SwingWorker para não bloquear a UI
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Simular processo de autenticação
                Thread.sleep(2000);
                
                // Verificação simples (apenas para demo)
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                GenericDao<Funcionario> dao =  new GenericDao<>(Funcionario.class);
                boolean name = false;
                ArrayList<Funcionario> all = dao.getAll();
                        for (Funcionario funcionario : all) {
                            if(funcionario.getEspecialidade().equalsIgnoreCase("vendedor")){
                                 name = username.equals(funcionario.geteMail()) && new String(password).equals(funcionario.getSenha());
                                 f = funcionario;
                            }
                    
                }
                        admin =  (username.equals("admin") && new String(password).equals("admin"));
                return  admin || name  ;
            }
            
            @Override
            protected void done() {
                try {
                    boolean success = get();
                    
                    if (success) {
                        statusLabel.setText("Login bem-sucedido! Redirecionando...");
                        statusLabel.setForeground(new Color(100, 200, 100));
                        
                        // Simular redirecionamento para o sistema principal
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                
                                    SplashScreen splash = new SplashScreen(!admin,f);
                                     splash.startSplash();
                               
                                
                                frame.dispose();                       }
                        }, 1500);
                        
                    } else {
                        statusLabel.setText("Usuário ou senha inválidos!");
                        statusLabel.setForeground(new Color(255, 100, 100));
                        
                        // Ativar os controles novamente
                        loginButton.setEnabled(true);
                        usernameField.setEnabled(true);
                        passwordField.setEnabled(true);
                        
                        // Limpar o campo de senha
                        passwordField.setText("");
                        
                        // Sacudir a tela para indicar erro
                        shakeWindow();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void shakeWindow() {
        final Point originalLocation = getLocation();
        final Timer timer = new Timer();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            int runs = 0;
            
            @Override
            public void run() {
                if (runs++ < 10) {
                    int offsetX = (int) (Math.sin(runs * Math.PI) * 10);
                    setLocation(originalLocation.x + offsetX, originalLocation.y);
                } else {
                    setLocation(originalLocation);
                    timer.cancel();
                }
            }
        }, 10, 30);
    }
    
    public static void main(String[] args) {
        try {
            // Aplicar um tema escuro padrão (não requer FlatLaf)
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                // Personalizar alguns componentes do tema
                UIManager.put("TextField.arc", 10);
                UIManager.put("Button.arc", 10);
                UIManager.put("Component.focusWidth", 1);
                UIManager.put("Component.focusColor", new Color(30, 144, 255));
                UIManager.put("ScrollBar.width", 12);
                UIManager.put("ScrollBar.thumbArc", 999);
                UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
                UIManager.put("TextComponent.arc", 5);
            } catch (Exception e) {
                System.out.println("Não foi possível carregar o look and feel: " + e);
                // Continua com o look and feel padrão
            }
            
            SwingUtilities.invokeLater(() -> {
                CarSystemLogin loginForm = new CarSystemLogin();
                
                // Mostrar janela com efeito de fade in se suportado pelo sistema
                try {
                    loginForm.setOpacity(0.0f);
                } catch (Exception e) {
                    // Opacidade não suportada, ignorar
                }
                
                loginForm.setVisible(true);
                
                // Animação de fade in se suportado
                try {
                    // Verificar se a opacidade é suportada
                    loginForm.setOpacity(0.1f);
                    
                    Timer fadeTimer = new Timer();
                    fadeTimer.scheduleAtFixedRate(new TimerTask() {
                        float opacity = 0.0f;
                        
                        @Override
                        public void run() {
                            opacity += 0.05f;
                            
                            if (opacity > 1.0f) {
                                opacity = 1.0f;
                                fadeTimer.cancel();
                            }
                            
                            loginForm.setOpacity(opacity);
                        }
                    }, 0, 30);
                } catch (Exception e) {
                    // Opacidade não suportada, ignorar
                    loginForm.setOpacity(1.0f);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}