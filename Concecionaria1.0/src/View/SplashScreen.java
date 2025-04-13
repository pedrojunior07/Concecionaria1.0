/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;



import Model.Funcionario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class SplashScreen extends JWindow {
    private JProgressBar progressBar;
    private int progress = 0;
    private Timer progressTimer;
    private final boolean isDarkMode;
    Funcionario f;
    
    public SplashScreen(boolean isDarkMode, Funcionario f) {
        this.f = f;
        this.isDarkMode = isDarkMode;
        setupSplash();
    }
    
    private void setupSplash() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background gradient
                Color topColor = isDarkMode ? new Color(33, 33, 33) : new Color(245, 245, 245);
                Color bottomColor = isDarkMode ? new Color(25, 25, 25) : new Color(225, 225, 225);
                GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border
                g2d.setColor(isDarkMode ? new Color(45, 45, 45) : new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                
                // Title text
                Font titleFont = new Font("Montserrat", Font.BOLD, 24);
                g2d.setFont(titleFont);
                g2d.setColor(isDarkMode ? Color.WHITE : Color.BLACK);
                
                String title = "Sistema de Vendas de Automóveis";
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (getWidth() - titleWidth) / 2, 80);
                
                // Version text
                Font versionFont = new Font("Montserrat", Font.PLAIN, 14);
                g2d.setFont(versionFont);
                g2d.setColor(isDarkMode ? new Color(200, 200, 200) : new Color(100, 100, 100));
                
                String version = "Versão 1.0";
                fm = g2d.getFontMetrics();
                int versionWidth = fm.stringWidth(version);
                g2d.drawString(version, (getWidth() - versionWidth) / 2, 110);
                
                // Draw a car icon or company logo
                // Here we'll simulate a simple car shape
                drawCarIcon(g2d, getWidth() / 2 - 50, 140, isDarkMode ? Color.WHITE : Color.BLACK);
                
                g2d.dispose();
            }
            
            private void drawCarIcon(Graphics2D g2d, int x, int y, Color color) {
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(2f));
                
                // Car body
                g2d.drawRoundRect(x, y + 20, 100, 35, 10, 10);
                
                // Car top
                g2d.drawLine(x + 25, y + 20, x + 35, y);
                g2d.drawLine(x + 35, y, x + 70, y);
                g2d.drawLine(x + 70, y, x + 80, y + 20);
                
                // Wheels
                g2d.fillOval(x + 15, y + 45, 25, 25);
                g2d.fillOval(x + 65, y + 45, 25, 25);
                
                // Wheel centers
                g2d.setColor(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
                g2d.fillOval(x + 22, y + 52, 11, 11);
                g2d.fillOval(x + 72, y + 52, 11, 11);
            }
        };
        mainPanel.setLayout(null);
        
        // Create a custom styled progress bar
        progressBar = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(isDarkMode ? new Color(45, 45, 45) : new Color(220, 220, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Progress
                Color startColor = new Color(25, 118, 210);
                Color endColor = new Color(66, 165, 245);
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor,
                        getWidth(), 0, endColor);
                
                g2d.setPaint(gradient);
                int progressWidth = (int)((getWidth() - 4) * ((double)getValue() / getMaximum()));
                g2d.fillRoundRect(2, 2, progressWidth, getHeight() - 4, 8, 8);
                
                g2d.dispose();
            }
        };
        progressBar.setStringPainted(false);
        progressBar.setBorderPainted(false);
        progressBar.setOpaque(false);
        progressBar.setBounds(50, 250, 400, 15);
        mainPanel.add(progressBar);
        
        // Loading text
        JLabel loadingLabel = new JLabel("Carregando...");
        loadingLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        loadingLabel.setForeground(isDarkMode ? Color.LIGHT_GRAY : Color.DARK_GRAY);
        loadingLabel.setBounds(50, 230, 400, 20);
        loadingLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(loadingLabel);
        
        // Status text that changes
        JLabel statusLabel = new JLabel("Inicializando...");
        statusLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        statusLabel.setForeground(isDarkMode ? Color.LIGHT_GRAY : Color.DARK_GRAY);
        statusLabel.setBounds(50, 270, 400, 20);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(statusLabel);
        
        // Timer to update progress and status
        String[] loadingMessages = {
            "Inicializando...",
            "Carregando banco de dados...",
            "Carregando interface...",
            "Verificando atualizações...",
            "Configurando ambiente...",
            "Carregando módulo de vendas...",
            "Carregando módulo de clientes...",
            "Verificando imagens de carros...",
            "Configurando relatórios...",
            "Finalizado!"
        };
        
        progressTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 2;
                progressBar.setValue(progress);
                
                // Update status message
                if (progress % 10 == 0) {
                    int messageIndex = progress / 10;
                    if (messageIndex < loadingMessages.length) {
                        statusLabel.setText(loadingMessages[messageIndex]);
                    }
                }
                
                if (progress >= 100) {
                    progressTimer.stop();
                    
                    // Fade out animation
                    Timer fadeTimer = new Timer(30, new ActionListener() {
                        float opacity = 1.0f;
                        
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            opacity -= 0.05f;
                            if (opacity <= 0) {
                                ((Timer)e.getSource()).stop();
                                dispose();
                                
                                // Start login screen
                                System.out.println(isDarkMode);    
                                if(isDarkMode){
                                SwingUtilities.invokeLater(() -> new Dashboard(f));}else{
                              SwingUtilities.invokeLater(() ->   new AdminFrame());
                                }
                            } else {
                                setOpacity(opacity);
                            }
                        }
                    });
                    fadeTimer.start();
                }
            }
        });
        
        getContentPane().add(mainPanel);
        setSize(500, 320);
        setLocationRelativeTo(null);
        setOpacity(0.0f);
        setShape(new RoundRectangle2D.Double(0, 0, 500, 320, 20, 20));
    }
    
    public void startSplash() {
        setVisible(true);
        
        // Fade in animation
        Timer fadeInTimer = new Timer(30, new ActionListener() {
            float opacity = 0.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    ((Timer)e.getSource()).stop();
                    
                    // Start progress timer after fade in completes
                    progressTimer.start();
                }
                setOpacity(opacity);
            }
        });
        fadeInTimer.start();
    }
    
    

}