package View.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 * A collection of custom UI components for the car sales system
 */
public class CustomComponents {
    
    /**
     * Creates a modern button with hover effects
     */
    public static class ModernButton extends JButton {
        private Color backgroundColor;
        private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;
        private Color textColor;
        private Color hoverTextColor;
        private boolean isHover = false;
        private boolean isPressed = false;
        private int radius = 15;
        
        public ModernButton(String text) {
            super(text);
            setup();
        }
        
        public ModernButton(String text, Color backgroundColor, Color textColor) {
            super(text);
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
            
            // Auto-generate hover and pressed colors
            this.hoverBackgroundColor = new Color(
                Math.min(backgroundColor.getRed() + 20, 255),
                Math.min(backgroundColor.getGreen() + 20, 255),
                Math.min(backgroundColor.getBlue() + 20, 255)
            );
            
            this.pressedBackgroundColor = new Color(
                Math.max(backgroundColor.getRed() - 20, 0),
                Math.max(backgroundColor.getGreen() - 20, 0),
                Math.max(backgroundColor.getBlue() - 20, 0)
            );
            
            this.hoverTextColor = textColor;
            
            setup();
        }
        
        private void setup() {
            if (backgroundColor == null) {
                backgroundColor = new Color(25, 118, 210);
                hoverBackgroundColor = new Color(41, 128, 214);
                pressedBackgroundColor = new Color(15, 108, 200);
                textColor = Color.WHITE;
                hoverTextColor = Color.WHITE;
            }
            
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(textColor);
            setFont(new Font("Montserrat", Font.BOLD, 14));
            setBorder(new EmptyBorder(10, 20, 10, 20));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHover = true;
                    setForeground(hoverTextColor);
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHover = false;
                    isPressed = false;
                    setForeground(textColor);
                    repaint();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Set background color based on state
            if (isPressed) {
                g2d.setColor(pressedBackgroundColor);
            } else if (isHover) {
                g2d.setColor(hoverBackgroundColor);
            } else {
                g2d.setColor(backgroundColor);
            }
            
            // Draw button background
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Add a subtle gradient effect
            Color topGradient = new Color(255, 255, 255, 30);
            Color bottomGradient = new Color(0, 0, 0, 30);
            GradientPaint gradient = new GradientPaint(
                    0, 0, topGradient,
                    0, getHeight(), bottomGradient);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Add a subtle glow effect when hovered
            if (isHover && !isPressed) {
                for (int i = 0; i < 3; i++) {
                    g2d.setColor(new Color(255, 255, 255, 50 - (i * 15)));
                    g2d.setStroke(new BasicStroke(i + 1));
                    g2d.drawRoundRect(i, i, getWidth() - (2 * i) - 1, getHeight() - (2 * i) - 1, radius, radius);
                }
            }
            
            // Draw text
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle textRect = fm.getStringBounds(getText(), g2d).getBounds();
            
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            g2d.drawString(getText(),
                    (getWidth() - textRect.width) / 2,
                    (getHeight() - textRect.height) / 2 + fm.getAscent());
            
            g2d.dispose();
        }
        
        public void setRadius(int radius) {
            this.radius = radius;
            repaint();
        }
        
        public void setColors(Color background, Color hover, Color pressed, Color text, Color hoverText) {
            this.backgroundColor = background;
            this.hoverBackgroundColor = hover;
            this.pressedBackgroundColor = pressed;
            this.textColor = text;
            this.hoverTextColor = hoverText;
            setForeground(textColor);
            repaint();
        }
    }
    
    /**
     * Creates a modern rounded text field
     */
    public static class ModernTextField extends JTextField {
        private Color borderColor = new Color(200, 200, 200);
        private Color focusBorderColor = new Color(25, 118, 210);
        private boolean isFocused = false;
        private String placeholder;
        private int radius = 10;
        
        public ModernTextField() {
            super();
            setup();
        }
        
        public ModernTextField(String text) {
            super(text);
            setup();
        }
        
        public ModernTextField(String text, String placeholder) {
            super(text);
            this.placeholder = placeholder;
            setup();
        }
        
        private void setup() {
            setOpaque(false);
            setBorder(new EmptyBorder(10, 15, 10, 15));
            setFont(new Font("Montserrat", Font.PLAIN, 14));
            
            addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent evt) {
                    isFocused = true;
                    repaint();
                }
                
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    isFocused = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Draw border
            g2d.setColor(isFocused ? focusBorderColor : borderColor);
            g2d.setStroke(new BasicStroke(isFocused ? 2f : 1f));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            
            // Draw placeholder if text is empty
            if (placeholder != null && getText().isEmpty() && !isFocused) {
                g2d.setColor(new Color(150, 150, 150));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(placeholder, 
                        getInsets().left, 
                        (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
            }
            
            g2d.dispose();
            
            // Use parent's text rendering
            super.paintComponent(g);
        }
        
        @Override
        public boolean contains(int x, int y) {
            Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
            return shape.contains(x, y);
        }
        
        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            repaint();
        }
        
        public void setRadius(int radius) {
            this.radius = radius;
            repaint();
        }
        
        public void setBorderColors(Color normal, Color focus) {
            this.borderColor = normal;
            this.focusBorderColor = focus;
            repaint();
        }
    }
    
    /**
     * Creates a modern panel with optional shadow and rounded corners
     */
    public static class ModernPanel extends JPanel {
        private int radius = 15;
        private boolean hasShadow = false;
        private Color shadowColor = new Color(0, 0, 0, 50);
        private int shadowSize = 5;
        
        public ModernPanel() {
            setup();
        }
        
        public ModernPanel(LayoutManager layout) {
            super(layout);
            setup();
        }
        
        public ModernPanel(boolean hasShadow) {
            this.hasShadow = hasShadow;
            setup();
        }
        
        public ModernPanel(LayoutManager layout, boolean hasShadow) {
            super(layout);
            this.hasShadow = hasShadow;
            setup();
        }
        
        private void setup() {
            setOpaque(false);
            if (hasShadow) {
                setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize * 2, shadowSize * 2));
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw shadow if enabled
            if (hasShadow) {
                for (int i = 0; i < shadowSize; i++) {
                    g2d.setColor(new Color(
                            shadowColor.getRed(),
                            shadowColor.getGreen(),
                            shadowColor.getBlue(),
                            shadowColor.getAlpha() / (i + 1)));
                    g2d.fillRoundRect(
                            shadowSize - i, 
                            shadowSize - i, 
                            getWidth() - (shadowSize * 2) + (i * 2), 
                            getHeight() - (shadowSize * 2) + (i * 2), 
                            radius, radius);
                }
            }
            
            // Draw panel background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(
                    hasShadow ? shadowSize : 0, 
                    hasShadow ? shadowSize : 0, 
                    getWidth() - (hasShadow ? shadowSize * 2 : 0), 
                    getHeight() - (hasShadow ? shadowSize * 2 : 0), 
                    radius, radius);
            
            g2d.dispose();
        }
        
        public void setRadius(int radius) {
            this.radius = radius;
            repaint();
        }
        
        public void setShadowSettings(boolean hasShadow, Color shadowColor, int shadowSize) {
            this.hasShadow = hasShadow;
            this.shadowColor = shadowColor;
            this.shadowSize = shadowSize;
            if (hasShadow) {
                setBorder(new EmptyBorder(shadowSize, shadowSize, shadowSize * 2, shadowSize * 2));
            } else {
                setBorder(null);
            }
            repaint();
        }
    }
    
    /**
     * Creates a modern sidebar menu button with icon support
     */
    public static class SidebarButton extends JButton {
        private boolean isActive = false;
        private boolean isHover = false;
        private Icon normalIcon;
        private Icon activeIcon;
        private int cornerRadius = 10;
        
        public SidebarButton(String text, Icon icon) {
            super(text);
            this.normalIcon = icon;
            this.activeIcon = icon; // Default to same icon for both states
            setup();
        }
        
        public SidebarButton(String text, Icon normalIcon, Icon activeIcon) {
            super(text);
            this.normalIcon = normalIcon;
            this.activeIcon = activeIcon;
            setup();
        }
        
        private void setup() {
            setIcon(normalIcon);
            setIconTextGap(15);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setFont(new Font("Montserrat", Font.PLAIN, 14));
            setBorder(new EmptyBorder(12, 20, 12, 20));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!isActive) {
                        isHover = true;
                        repaint();
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHover = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw background based on state
            if (isActive) {
                // Active state background
                g2d.setColor(new Color(25, 118, 210));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, cornerRadius, cornerRadius);
                
                // Set active icon
                if (activeIcon != null) {
                    setIcon(activeIcon);
                }
                setForeground(Color.WHITE);
            } else if (isHover) {
                // Hover state background
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, cornerRadius, cornerRadius);
                
                // Set normal icon
                if (normalIcon != null) {
                    setIcon(normalIcon);
                }
                setForeground(getForeground());
            } else {
                // Normal state - just set normal icon
                if (normalIcon != null) {
                    setIcon(normalIcon);
                }
                setForeground(getForeground());
            }
            
            g2d.dispose();
            super.paintComponent(g);
        }
        
        public void setActive(boolean active) {
            this.isActive = active;
            repaint();
        }
    }
    
    /**
     * Creates a modern table with customized look
     */
    public static class ModernTable extends JTable {
        private Color headerBackground = new Color(245, 245, 245);
        private Color headerForeground = new Color(60, 60, 60);
        private Color selectionBackground = new Color(25, 118, 210, 40);
        private Color gridColor = new Color(230, 230, 230);
        private int rowHeight = 40;
        
        public ModernTable() {
            setup();
        }
        
        public ModernTable(TableModel model) {
            super(model);
            setup();
        }
        
        private void setup() {
            // General table settings
            setShowGrid(true);
            setGridColor(gridColor);
            setRowHeight(rowHeight);
            setSelectionBackground(selectionBackground);
            setSelectionForeground(getForeground());
           setFont(new Font("Montserrat", Font.PLAIN, 13));
            setRowHeight(rowHeight);
            setIntercellSpacing(new Dimension(10, 5));
            setFillsViewportHeight(true);
            
            // Setup table header
            JTableHeader header = getTableHeader();
            header.setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus,
                                                            int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    
                    label.setBackground(headerBackground);
                    label.setForeground(headerForeground);
                    label.setFont(new Font("Montserrat", Font.BOLD, 13));
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    return label;
                }
            });
            header.setReorderingAllowed(false);
            
            // Setup cell renderer
            setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus,
                                                            int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    
                    label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    
                    // Alternate row colors
                    if (!isSelected) {
                        label.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                    }
                    
                    return label;
                }
            });
        }
        
        public void setHeaderColors(Color background, Color foreground) {
            this.headerBackground = background;
            this.headerForeground = foreground;
            repaint();
        }
        
        public void setTableColors(Color selectionBg, Color grid) {
            this.selectionBackground = selectionBg;
            this.gridColor = grid;
            setSelectionBackground(selectionBg);
            setGridColor(grid);
            repaint();
        }
    }
    
    /**
     * Creates a modern checkbox with animation
     */
    public static class ModernCheckBox extends JCheckBox {
        private Color uncheckedColor = new Color(200, 200, 200);
        private Color checkedColor = new Color(25, 118, 210);
        private Color textColor = new Color(60, 60, 60);
        private float animationState = 0f; // 0 = unchecked, 1 = checked
        private Timer animationTimer;
        
        public ModernCheckBox(String text) {
            super(text);
            setup();
        }
        
        public ModernCheckBox(String text, boolean selected) {
            super(text, selected);
            this.animationState = selected ? 1f : 0f;
            setup();
        }
        
        private void setup() {
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Montserrat", Font.PLAIN, 14));
            setForeground(textColor);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setIconTextGap(10);
            
            // Animation timer
            animationTimer = new Timer(16, e -> {
                if (isSelected() && animationState < 1f) {
                    animationState = Math.min(1f, animationState + 0.12f);
                    repaint();
                } else if (!isSelected() && animationState > 0f) {
                    animationState = Math.max(0f, animationState - 0.12f);
                    repaint();
                } else {
                    ((Timer)e.getSource()).stop();
                }
            });
            
            addItemListener(e -> {
                animationTimer.start();
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Get dimensions
            int checkSize = 20;
            int x = 0;
            int y = (getHeight() - checkSize) / 2;
            
            // Draw the checkbox background
            g2d.setColor(interpolateColor(uncheckedColor, checkedColor, animationState));
            g2d.fillRoundRect(x, y, checkSize, checkSize, 5, 5);
            
            // Draw the check mark
            if (animationState > 0) {
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2f));
                
                // Calculate check mark points with animation
                int[] xPoints = {
                    x + (int)(5 + 1 * animationState),
                    x + (int)(9 + 1 * animationState),
                    x + (int)(16)
                };
                
                int[] yPoints = {
                    y + (int)(checkSize - 6),
                    y + (int)(checkSize - 3),
                    y + (int)(5 + 3 * animationState)
                };
                
                g2d.drawPolyline(xPoints, yPoints, 3);
            }
            
            g2d.dispose();
            
            // Reset icon to not draw the default checkbox
            Icon oldIcon = getIcon();
            setIcon(null);
            
            // Draw the text
            super.paintComponent(g);
            
            // Restore the icon
            setIcon(oldIcon);
        }
        
        private Color interpolateColor(Color c1, Color c2, float ratio) {
            int r = Math.round(c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);
            int g = Math.round(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
            int b = Math.round(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);
            int a = Math.round(c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * ratio);
            return new Color(r, g, b, a);
        }
        
        public void setColors(Color uncheckedColor, Color checkedColor, Color textColor) {
            this.uncheckedColor = uncheckedColor;
            this.checkedColor = checkedColor;
            this.textColor = textColor;
            setForeground(textColor);
            repaint();
        }
    }
    
    /**
     * Creates a modern radio button with animation
     */
    public static class ModernRadioButton extends JRadioButton {
        private Color uncheckedColor = new Color(200, 200, 200);
        private Color checkedColor = new Color(25, 118, 210);
        private Color textColor = new Color(60, 60, 60);
        private float animationState = 0f; // 0 = unchecked, 1 = checked
        private Timer animationTimer;
        
        public ModernRadioButton(String text) {
            super(text);
            setup();
        }
        
        public ModernRadioButton(String text, boolean selected) {
            super(text, selected);
            this.animationState = selected ? 1f : 0f;
            setup();
        }
        
        private void setup() {
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Montserrat", Font.PLAIN, 14));
            setForeground(textColor);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setIconTextGap(10);
            
            // Animation timer
            animationTimer = new Timer(16, e -> {
                if (isSelected() && animationState < 1f) {
                    animationState = Math.min(1f, animationState + 0.12f);
                    repaint();
                } else if (!isSelected() && animationState > 0f) {
                    animationState = Math.max(0f, animationState - 0.12f);
                    repaint();
                } else {
                    ((Timer)e.getSource()).stop();
                }
            });
            
            addItemListener(e -> {
                animationTimer.start();
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Get dimensions
            int radioSize = 20;
            int x = 0;
            int y = (getHeight() - radioSize) / 2;
            
            // Draw the radio button border
            g2d.setColor(interpolateColor(uncheckedColor, checkedColor, animationState));
            g2d.fillOval(x, y, radioSize, radioSize);
            
            // Draw the radio button inner circle with animation
            if (animationState > 0) {
                g2d.setColor(Color.WHITE);
                int innerSize = (int)(radioSize * 0.5f * animationState);
                int innerX = x + (radioSize - innerSize) / 2;
                int innerY = y + (radioSize - innerSize) / 2;
                g2d.fillOval(innerX, innerY, innerSize, innerSize);
            }
            
            g2d.dispose();
            
            // Reset icon to not draw the default radio button
            Icon oldIcon = getIcon();
            setIcon(null);
            
            // Draw the text
            super.paintComponent(g);
            
            // Restore the icon
            setIcon(oldIcon);
        }
        
        private Color interpolateColor(Color c1, Color c2, float ratio) {
            int r = Math.round(c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);
            int g = Math.round(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
            int b = Math.round(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);
            int a = Math.round(c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * ratio);
            return new Color(r, g, b, a);
        }
        
        public void setColors(Color uncheckedColor, Color checkedColor, Color textColor) {
            this.uncheckedColor = uncheckedColor;
            this.checkedColor = checkedColor;
            this.textColor = textColor;
            setForeground(textColor);
            repaint();
        }
    }
    
    /**
     * Creates a modern toggle switch
     */
    public static class ToggleSwitch extends JToggleButton {
        private Color toggleOffBackground = new Color(200, 200, 200);
        private Color toggleOnBackground = new Color(25, 118, 210);
        private Color thumbOffColor = Color.WHITE;
        private Color thumbOnColor = Color.WHITE;
        private float animationState = 0f; // 0 = off, 1 = on
        private Timer animationTimer;
        
        public ToggleSwitch() {
            setup();
        }
        
        public ToggleSwitch(boolean selected) {
//            super(null, selected);
            this.animationState = selected ? 1f : 0f;
            setup();
        }
        
        private void setup() {
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Set preferred size
            setPreferredSize(new Dimension(60, 30));
            
            // Animation timer
            animationTimer = new Timer(16, e -> {
                if (isSelected() && animationState < 1f) {
                    animationState = Math.min(1f, animationState + 0.12f);
                    repaint();
                } else if (!isSelected() && animationState > 0f) {
                    animationState = Math.max(0f, animationState - 0.12f);
                    repaint();
                } else {
                    ((Timer)e.getSource()).stop();
                }
            });
            
            addItemListener(e -> {
                animationTimer.start();
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Get dimensions
            int width = getWidth();
            int height = getHeight();
            int toggleWidth = Math.min(width, height * 2);
            int toggleHeight = height - 4;
            int x = (width - toggleWidth) / 2;
            int y = (height - toggleHeight) / 2;
            
            // Draw the toggle background
            g2d.setColor(interpolateColor(toggleOffBackground, toggleOnBackground, animationState));
            g2d.fillRoundRect(x, y, toggleWidth, toggleHeight, toggleHeight, toggleHeight);
            
            // Draw the thumb with animation
            int thumbSize = toggleHeight - 4;
            int thumbY = y + 2;
            int thumbOffX = x + 2;
            int thumbOnX = x + toggleWidth - thumbSize - 2;
            int thumbX = (int) (thumbOffX + (thumbOnX - thumbOffX) * animationState);
            
            // Add a subtle shadow to the thumb
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillOval(thumbX + 1, thumbY + 1, thumbSize, thumbSize);
            
            // Draw the thumb
            g2d.setColor(interpolateColor(thumbOffColor, thumbOnColor, animationState));
            g2d.fillOval(thumbX, thumbY, thumbSize, thumbSize);
            
            g2d.dispose();
        }
        
        private Color interpolateColor(Color c1, Color c2, float ratio) {
            int r = Math.round(c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);
            int g = Math.round(c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
            int b = Math.round(c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);
            int a = Math.round(c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * ratio);
            return new Color(r, g, b, a);
        }
        
        public void setColors(Color offBg, Color onBg, Color offThumb, Color onThumb) {
            this.toggleOffBackground = offBg;
            this.toggleOnBackground = onBg;
            this.thumbOffColor = offThumb;
            this.thumbOnColor = onThumb;
            repaint();
        }
    }
    
    /**
     * Creates a notification badge component
     */
    public static class NotificationBadge extends JComponent {
        private int count;
        private Color backgroundColor = new Color(220, 53, 69);
        private Color textColor = Color.WHITE;
        private int size = 20;
        
        public NotificationBadge() {
            this(0);
        }
        
        public NotificationBadge(int count) {
            this.count = count;
            setPreferredSize(new Dimension(size, size));
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            if (count <= 0) return;
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            String displayText = count > 99 ? "99+" : String.valueOf(count);
            int width = getFontMetrics(getFont()).stringWidth(displayText) + 10;
            width = Math.max(width, size);
            
            // Draw badge circle
            g2d.setColor(backgroundColor);
            g2d.fillOval(0, 0, size, size);
            
            // Draw text if it fits
            g2d.setColor(textColor);
            g2d.setFont(new Font("Montserrat", Font.BOLD, 10));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(displayText);
            g2d.drawString(displayText, 
                    (size - textWidth) / 2, 
                    (size - fm.getHeight()) / 2 + fm.getAscent());
            
            g2d.dispose();
        }
        
        public void setCount(int count) {
            this.count = count;
            repaint();
        }
        
        public int getCount() {
            return count;
        }
        
        public void setColors(Color backgroundColor, Color textColor) {
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
            repaint();
        }
        
        public void setSize(int size) {
            this.size = size;
            setPreferredSize(new Dimension(size, size));
            repaint();
        }
    }
    
    /**
     * Creates a modern progress bar with animation
     */
    public static class ModernProgressBar extends JProgressBar {
        private Color trackColor = new Color(230, 230, 230);
        private Color progressColor = new Color(25, 118, 210);
        private boolean gradient = true;
        private boolean animated = true;
        private Timer animationTimer;
        private int animationOffset = 0;
        
        public ModernProgressBar() {
            setup();
        }
        
        public ModernProgressBar(int min, int max) {
            super(min, max);
            setup();
        }
        
        private void setup() {
            setOpaque(false);
            setBorderPainted(false);
            setStringPainted(false);
            
            // Animation timer for indeterminate mode
            animationTimer = new Timer(30, e -> {
                if (isIndeterminate()) {
                    animationOffset = (animationOffset + 5) % 200;
                    repaint();
                }
            });
            animationTimer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Draw track background
            g2d.setColor(trackColor);
            g2d.fillRoundRect(0, 0, width, height, height, height);
            
            if (isIndeterminate()) {
                // Draw animated indeterminate state
                if (animated) {
                    // Create gradient for animated portion
                    Color startColor = new Color(progressColor.getRed(), progressColor.getGreen(), progressColor.getBlue(), 50);
                    Color midColor = progressColor;
                    Color endColor = new Color(progressColor.getRed(), progressColor.getGreen(), progressColor.getBlue(), 50);
                    
                    float[] fractions = {0.0f, 0.5f, 1.0f};
                    Color[] colors = {startColor, midColor, endColor};
                    LinearGradientPaint paint = new LinearGradientPaint(
                            -50 + animationOffset, 0, 
                            50 + animationOffset, 0, 
                            fractions, colors);
                    
                    g2d.setPaint(paint);
                    g2d.fillRoundRect(0, 0, width, height, height, height);
                } else {
                    // Non-animated indeterminate
                    g2d.setColor(progressColor);
                    g2d.fillRoundRect(0, 0, width / 3, height, height, height);
                }
            } else {
                // Draw determinate progress
                int progressWidth = (int) (width * getPercentComplete());
                if (progressWidth > 0) {
                    if (gradient) {
                        // Create gradient for progress
                        GradientPaint paint = new GradientPaint(
                                0, 0, progressColor, 
                                progressWidth, 0, 
                                new Color(progressColor.getRed() + 20, progressColor.getGreen() + 20, progressColor.getBlue() + 20));
                        g2d.setPaint(paint);
                    } else {
                        g2d.setColor(progressColor);
                    }
                    g2d.fillRoundRect(0, 0, progressWidth, height, height, height);
                }
            }
            
            g2d.dispose();
        }
        
        public void setAnimated(boolean animated) {
            this.animated = animated;
            if (animated) {
                animationTimer.start();
            } else {
                animationTimer.stop();
            }
            repaint();
        }
        
        public void setGradient(boolean gradient) {
            this.gradient = gradient;
            repaint();
        }
        
        public void setColors(Color trackColor, Color progressColor) {
            this.trackColor = trackColor;
            this.progressColor = progressColor;
            repaint();
        }
        
        @Override
        public void setIndeterminate(boolean indeterminate) {
            super.setIndeterminate(indeterminate);
            if (indeterminate && animated) {
                animationTimer.start();
            } else if (!indeterminate) {
                animationTimer.stop();
            }
        }
    }
}