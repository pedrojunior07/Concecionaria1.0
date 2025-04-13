/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;




import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginScreen extends JFrame {
    // Main components
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JToggleButton themeToggle;
    private boolean isDarkMode = false;
    private JLabel statusLabel;
    private JPanel carImagesPanel;
    private Timer slideshowTimer;
    private int currentImageIndex = 0;
    
    // Animation components
    private Timer pulseTimer;
    private float pulseValue = 0.0f;
    private boolean pulseIncreasing = true;
    private List<ParticleEffect> particles = new ArrayList<>();
    private Timer particleTimer;
    private Timer timeUpdateTimer;
    private JLabel timeLabel;
    private JLabel dateLabel;
    
    // Car images for slideshow
    private List<String> carImagePaths = new ArrayList<>();
    private List<ImageIcon> carImages = new ArrayList<>();
    
    // Constants
    private static final Color ACCENT_COLOR_LIGHT = new Color(25, 118, 210);
    private static final Color ACCENT_COLOR_DARK = new Color(66, 165, 245);
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 600;

    public LoginScreen() {
        setupWindow();
        loadCarImages();
        initializeComponents();
        setupAnimations();
        setupLayout();
        setupActionListeners();
        
        setVisible(true);
    }
    
    private void setupWindow() {
        // Set up the FlatLaf look and feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle("Sistema de Gerenciamento de Veículos");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true); // Remove window decorations for sleek look
        setShape(new RoundRectangle2D.Double(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 20, 20)); // Rounded corners
    }
    
    private void loadCarImages() {
        // In production, replace with actual file paths
        // Here we're simulating images for demonstration
        String[] demoImages = {
            "resources/cars/car1.jpg",
            "resources/cars/car2.jpg",
            "resources/cars/car3.jpg",
            "resources/cars/car4.jpg",
            "resources/cars/car5.jpg"
        };
        
        for (String path : demoImages) {
            carImagePaths.add(path);
            // Create a gradient placeholder since we don't have actual files
            BufferedImage placeholderImage = createGradientImage(450, WINDOW_HEIGHT);
            carImages.add(new ImageIcon(placeholderImage));
        }
    }
    
    private BufferedImage createGradientImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Create a random gradient for demonstration
        Random random = new Random();
        Color color1 = new Color(random.nextInt(100), random.nextInt(100), random.nextInt(150) + 100);
        Color color2 = new Color(random.nextInt(100) + 150, random.nextInt(100) + 150, random.nextInt(100));
        
        GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
        
        // Add some shapes for visual interest
        g2d.setColor(new Color(255, 255, 255, 50));
        int numShapes = random.nextInt(5) + 5;
        for (int i = 0; i < numShapes; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int size = random.nextInt(150) + 50;
            g2d.fillOval(x, y, size, size);
        }
        
        g2d.dispose();
        return image;
    }
    
    private void initializeComponents() {
        // Main containers
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
        
        // Left panel (image slideshow)
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(450, WINDOW_HEIGHT));
        
        carImagesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!carImages.isEmpty()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    ImageIcon currentImage = carImages.get(currentImageIndex);
                    g2d.drawImage(currentImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
                    
                    // Overlay with semi-transparent dark layer
                    g2d.setColor(new Color(0, 0, 0, 120));
                    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
                    
                    // Draw car sales system title
                    g2d.setFont(new Font("Montserrat", Font.BOLD, 24));
                    g2d.setColor(Color.WHITE);
                    String title = "SISTEMA DE VENDAS DE AUTOMÓVEIS";
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(title);
                    g2d.drawString(title, (getWidth() - textWidth) / 2, 60);
                    
                    // Draw subtitle
                    g2d.setFont(new Font("Montserrat", Font.PLAIN, 16));
                    String subtitle = "Gestão profissional para o seu negócio";
                    textWidth = fm.stringWidth(subtitle);
                    g2d.drawString(subtitle, (getWidth() - textWidth) / 2, 90);
                    
                    g2d.dispose();
                }
            }
        };
        
        // Right panel (login form)
        rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw particles
                for (ParticleEffect particle : particles) {
                    particle.render(g2d);
                }
                
                g2d.dispose();
            }
        };
        rightPanel.setLayout(null); // Using absolute positioning
        
        // Time and date labels
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Montserrat", Font.BOLD, 24));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Username field
        usernameField = new RoundedJTextField(20);
        usernameField.setFont(new Font("Montserrat", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                usernameField.getBorder(),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        
        // Password field
        passwordField = new RoundedJPasswordField(20);
        passwordField.setFont(new Font("Montserrat", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        
        // Username and password labels
        JLabel usernameLabel = new JLabel("Nome de Usuário");
        usernameLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        
        JLabel passwordLabel = new JLabel("Senha");
        passwordLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        
        // Login button with pulsing effect
        loginButton = new JButton("ENTRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw button background with gradient
                Color topColor = isDarkMode ? ACCENT_COLOR_DARK : ACCENT_COLOR_LIGHT;
                Color bottomColor = isDarkMode ? 
                        new Color(ACCENT_COLOR_DARK.getRed() - 30, ACCENT_COLOR_DARK.getGreen() - 30, ACCENT_COLOR_DARK.getBlue() - 30) :
                        new Color(ACCENT_COLOR_LIGHT.getRed() - 30, ACCENT_COLOR_LIGHT.getGreen() - 30, ACCENT_COLOR_LIGHT.getBlue() - 30);
                
                GradientPaint gradient = new GradientPaint(
                        0, 0, topColor, 
                        0, getHeight(), bottomColor);
                
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw pulsing glow effect
                if (isEnabled()) {
                    Color glowColor = new Color(topColor.getRed(), 
                                            topColor.getGreen(), 
                                            topColor.getBlue(), 
                                            (int)(70 * pulseValue));
                    for (int i = 0; i < 5; i++) {
                        g2d.setColor(new Color(glowColor.getRed(), 
                                           glowColor.getGreen(), 
                                           glowColor.getBlue(), 
                                           glowColor.getAlpha() / (i+1)));
                        g2d.setStroke(new BasicStroke(i * 2));
                        g2d.drawRoundRect(i, i, getWidth() - 2*i - 1, getHeight() - 2*i - 1, 15, 15);
                    }
                }
                
                // Draw button text
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle textRect = fm.getStringBounds(getText(), g2d).getBounds();
                
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(),
                        (getWidth() - textRect.width) / 2,
                        (getHeight() - textRect.height) / 2 + fm.getAscent());
                
                g2d.dispose();
            }
        };
        loginButton.setFont(new Font("Montserrat", Font.BOLD, 14));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        
        // Theme toggle
        themeToggle = new JToggleButton("☀");  // Moon symbol when unchecked (light mode)
        themeToggle.setFont(new Font("Monospaced", Font.BOLD, 18));
        themeToggle.setBorder(new EmptyBorder(5, 5, 5, 5));
        themeToggle.setFocusPainted(false);
        themeToggle.setContentAreaFilled(false);
        
        // Close button
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        closeButton.setForeground(Color.GRAY);
        closeButton.setBorder(new EmptyBorder(5, 5, 5, 5));
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(e -> System.exit(0));
        
        // Status label for login result
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add close button to right panel top-right
        closeButton.setBounds(WINDOW_WIDTH - 450 - 40, 10, 30, 30);
        rightPanel.add(closeButton);
        
        // Add theme toggle to right panel top-right
        themeToggle.setBounds(WINDOW_WIDTH - 450 - 70, 10, 30, 30);
        rightPanel.add(themeToggle);
        
        // Add time and date to right panel top
        timeLabel.setBounds(WINDOW_WIDTH - 450 - 200, 10, 120, 30);
        dateLabel.setBounds(WINDOW_WIDTH - 450 - 200, 40, 120, 20);
        rightPanel.add(timeLabel);
        rightPanel.add(dateLabel);
        
        // Add login form components with positioning
        JLabel loginTitle = new JLabel("Bem-vindo de volta!");
        loginTitle.setFont(new Font("Montserrat", Font.BOLD, 24));
        loginTitle.setBounds(70, 100, 400, 40);
        rightPanel.add(loginTitle);
        
        JLabel loginSubtitle = new JLabel("Entre com suas credenciais para acessar o sistema");
        loginSubtitle.setFont(new Font("Montserrat", Font.PLAIN, 14));
        loginSubtitle.setBounds(70, 140, 400, 30);
        rightPanel.add(loginSubtitle);
        
        usernameLabel.setBounds(70, 200, 200, 20);
        rightPanel.add(usernameLabel);
        
        usernameField.setBounds(70, 225, 310, 45);
        rightPanel.add(usernameField);
        
        passwordLabel.setBounds(70, 290, 200, 20);
        rightPanel.add(passwordLabel);
        
        passwordField.setBounds(70, 315, 310, 45);
        rightPanel.add(passwordField);
        
        loginButton.setBounds(70, 400, 310, 50);
        rightPanel.add(loginButton);
        
        statusLabel.setBounds(70, 460, 310, 30);
        rightPanel.add(statusLabel);
    }
    
    private void setupAnimations() {
        // Setup image slideshow timer
        slideshowTimer = new Timer(5000, e -> {
            currentImageIndex = (currentImageIndex + 1) % carImages.size();
            carImagesPanel.repaint();
        });
        slideshowTimer.start();
        
        // Pulse effect for login button
        pulseTimer = new Timer(50, e -> {
            if (pulseIncreasing) {
                pulseValue += 0.05f;
                if (pulseValue >= 1.0f) {
                    pulseValue = 1.0f;
                    pulseIncreasing = false;
                }
            } else {
                pulseValue -= 0.05f;
                if (pulseValue <= 0.0f) {
                    pulseValue = 0.0f;
                    pulseIncreasing = true;
                }
            }
            loginButton.repaint();
        });
        pulseTimer.start();
        
        // Particle animation timer
        for (int i = 0; i < 20; i++) {
            particles.add(new ParticleEffect(WINDOW_WIDTH - 450, WINDOW_HEIGHT));
        }
        
        particleTimer = new Timer(50, e -> {
            for (ParticleEffect particle : particles) {
                particle.update();
            }
            rightPanel.repaint();
        });
        particleTimer.start();
        
        // Clock update timer
        timeUpdateTimer = new Timer(1000, e -> updateTimeAndDate());
        timeUpdateTimer.start();
        updateTimeAndDate();
    }
    
    private void updateTimeAndDate() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        timeLabel.setText(now.format(timeFormatter));
        
        java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        dateLabel.setText(dateFormatter.format(new java.util.Date()));
        
        if (isDarkMode) {
            timeLabel.setForeground(Color.WHITE);
            dateLabel.setForeground(Color.LIGHT_GRAY);
        } else {
            timeLabel.setForeground(Color.BLACK);
            dateLabel.setForeground(Color.DARK_GRAY);
        }
    }
    
    private void setupLayout() {
        leftPanel.add(carImagesPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void setupActionListeners() {
        // Theme toggle
        themeToggle.addActionListener(e -> {
            isDarkMode = themeToggle.isSelected();
            if (isDarkMode) {
                themeToggle.setText("☾");  // Sun symbol when checked (dark mode)
                applyDarkTheme();
            } else {
                themeToggle.setText("☀");  // Moon symbol when unchecked (light mode)
                applyLightTheme();
            }
        });
        
        // Login button action
        loginButton.addActionListener(e -> performLogin());
        
        // Enter key in password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        
        // Window drag handling
        DragListener dragListener = new DragListener();
        leftPanel.addMouseListener(dragListener);
        leftPanel.addMouseMotionListener(dragListener);
        rightPanel.addMouseListener(dragListener);
        rightPanel.addMouseMotionListener(dragListener);
    }
    
    private void performLogin() {
        // Get username and password
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        // Disable login button
        loginButton.setEnabled(false);
        
        // Animated login simulation
        Timer timer = new Timer(100, new ActionListener() {
            int counter = 0;
            String dots = "";
            
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                
                if (counter <= 10) {
                    dots = dots.length() >= 3 ? "" : dots + ".";
                    statusLabel.setText("Autenticando" + dots);
                    statusLabel.setForeground(isDarkMode ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                } else {
                    ((Timer)e.getSource()).stop();
                    
                    // Check credentials (Just for demo, replace with actual auth)
                    if (username.equals("admin") && password.equals("admin")) {
                        statusLabel.setText("Login bem-sucedido!");
                        statusLabel.setForeground(new Color(76, 175, 80)); // Green
                        
                        // Start dashboard after delay
                        Timer successTimer = new Timer(1000, e2 -> {
                            slideshowTimer.stop();
                            pulseTimer.stop();
                            particleTimer.stop();
                            timeUpdateTimer.stop();
                            
                            // Launch main application
                            SwingUtilities.invokeLater(() -> {
                                dispose();
                                try {
                                    // Launch the main dashboard
//                                    new Dashboard();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        });
                        successTimer.setRepeats(false);
                        successTimer.start();
                    } else {
                        statusLabel.setText("Nome de usuário ou senha incorretos");
                        statusLabel.setForeground(new Color(244, 67, 54)); // Red
                        loginButton.setEnabled(true);
                        
                        // Shake the form to indicate error
                        shakeComponent(usernameField);
                        shakeComponent(passwordField);
                    }
                }
            }
        });
        timer.start();
    }
    
    private void shakeComponent(Component component) {
        final int originalX = component.getLocation().x;
        final int originalY = component.getLocation().y;
        
        Timer shakeTimer = new Timer(30, new ActionListener() {
            int count = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = originalX;
                if (count < 10) {
                    x += (count % 2 == 0) ? 5 : -5;
                    component.setLocation(x, originalY);
                    count++;
                } else {
                    component.setLocation(originalX, originalY);
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        shakeTimer.start();
    }
    
    private void applyDarkTheme() {
        FlatAnimatedLafChange.showSnapshot();
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        rightPanel.setBackground(new Color(33, 33, 33));
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        
        Component[] components = rightPanel.getComponents();
        for (Component c : components) {
            if (c instanceof JLabel && c != statusLabel) {
                c.setForeground(Color.WHITE);
            }
        }
        
        themeToggle.setForeground(Color.WHITE);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
    
    private void applyLightTheme() {
        FlatAnimatedLafChange.showSnapshot();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        rightPanel.setBackground(Color.WHITE);
        usernameField.setBackground(new Color(240, 240, 240));
        usernameField.setForeground(Color.BLACK);
        passwordField.setBackground(new Color(240, 240, 240));
        passwordField.setForeground(Color.BLACK);
        
        Component[] components = rightPanel.getComponents();
        for (Component c : components) {
            if (c instanceof JLabel && c != statusLabel) {
                c.setForeground(Color.BLACK);
            }
        }
        
        themeToggle.setForeground(Color.BLACK);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
    
    // Custom components and helper classes
    
    // Rounded JTextField
    static class RoundedJTextField extends JTextField {
        private Shape shape;
        
        public RoundedJTextField(int size) {
            super(size);
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            super.paintComponent(g);
        }
        
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground().darker());
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        
        @Override
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }
    
    // Rounded JPasswordField
    static class RoundedJPasswordField extends JPasswordField {
        private Shape shape;
        
        public RoundedJPasswordField(int size) {
            super(size);
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            super.paintComponent(g);
        }
        
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground().darker());
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        
        @Override
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }
    
    // Particle animation effect
    class ParticleEffect {
        private float x, y;
        private float speed;
        private float size;
        private float alpha;
        private Color color;
        private int maxWidth, maxHeight;
        
        public ParticleEffect(int maxWidth, int maxHeight) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            reset();
        }
        
        private void reset() {
            Random rand = new Random();
            x = rand.nextInt(maxWidth);
            y = maxHeight + rand.nextInt(50);
            speed = rand.nextFloat() * 2 + 0.5f;
            size = rand.nextFloat() * 4 + 2;
            alpha = rand.nextFloat() * 0.3f + 0.1f;
            
            // Choose a color for the particle
            if (isDarkMode) {
                color = new Color(rand.nextInt(100) + 155, 
                              rand.nextInt(100) + 155, 
                              rand.nextInt(100) + 155);
            } else {
                color = new Color(rand.nextInt(100), 
                              rand.nextInt(100), 
                              rand.nextInt(200));
            }
        }
        
        public void update() {
            y -= speed;
            if (y < -10) {
                reset();
            }
        }
        
        public void render(Graphics2D g2d) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(color);
            g2d.fillOval((int)x, (int)y, (int)size, (int)size);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
    
    // Window dragging functionality
    class DragListener extends MouseAdapter {
        private Point startPoint;
        
        @Override
        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            startPoint.translate(e.getComponent().getLocation().x, e.getComponent().getLocation().y);
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            Point dragPoint = e.getPoint();
            dragPoint.translate(e.getComponent().getLocation().x, e.getComponent().getLocation().y);
            
            Point location = getLocation();
            location.translate(dragPoint.x - startPoint.x, dragPoint.y - startPoint.y);
            setLocation(location);
            
            startPoint = dragPoint;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system properties for better font rendering
                System.setProperty("awt.useSystemAAFontSettings", "on");
                System.setProperty("swing.aatext", "true");
                
//              
//UIManager.registerLookAndFeel("FlatLaf Light", FlatLightLaf.class.getName());
//                UIManager.registerLookAndFeel("FlatLaf Dark", FlatDarkLaf.class.getName());
//                
                // Load custom fonts if available
                try {
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Montserrat-Regular.ttf")));
                    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Montserrat-Bold.ttf")));
                } catch (IOException | FontFormatException e) {
                    // If the custom font is not available, fallback to system fonts
                    System.out.println("Warning: Custom fonts not found. Using system fonts instead.");
                }
                
                new LoginScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}