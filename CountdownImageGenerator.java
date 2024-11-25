import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CountdownImageGenerator {

    public static void main(String[] args) {
        int daysLeft = 50; // Days remaining
        int totalDays = 100; // Total days
        String gradientType = "soft_sunrise"; // Gradient style

        createCountdownImage(daysLeft, totalDays, gradientType);
    }

    public static void createCountdownImage(int daysLeft, int totalDays, String gradientType) {
        int width = 1920;
        int height = 1080;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        Color[] gradient = getGradientColors(gradientType);
        if (gradient == null) {
            System.err.println("Invalid gradient type.");
            return;
        }

        // Create gradient background
        for (int y = 0; y < height; y++) {
            float ratio = (float) y / height;
            int red = (int) (gradient[0].getRed() + ratio * (gradient[1].getRed() - gradient[0].getRed()));
            int green = (int) (gradient[0].getGreen() + ratio * (gradient[1].getGreen() - gradient[0].getGreen()));
            int blue = (int) (gradient[0].getBlue() + ratio * (gradient[1].getBlue() - gradient[0].getBlue()));
            g2d.setColor(new Color(red, green, blue));
            g2d.drawLine(0, y, width, y);
        }

        // Fonts
        Font mainFont = new Font("Arial", Font.BOLD, 100);
        Font secondaryFont = new Font("Arial", Font.BOLD, 80);

        // Secondary text
        String secondaryText = "Discipline today, Dominance tomorrow";
        FontMetrics metrics = g2d.getFontMetrics(secondaryFont);
        int textWidth = metrics.stringWidth(secondaryText);
        int xSecondary = (width - textWidth) / 2;
        int ySecondary = height / 3;
        g2d.setColor(Color.GRAY);
        g2d.setFont(secondaryFont);
        g2d.drawString(secondaryText, xSecondary + 3, ySecondary + 3);
        g2d.setColor(Color.BLACK);
        g2d.drawString(secondaryText, xSecondary, ySecondary);

        // Main text
        String mainText = "D-Day ~ " + daysLeft + " Days left";
        metrics = g2d.getFontMetrics(mainFont);
        textWidth = metrics.stringWidth(mainText);
        int xMain = (width - textWidth) / 2;
        int yMain = ySecondary + metrics.getHeight() + 50;
        g2d.setColor(Color.BLACK);
        g2d.setFont(mainFont);
        g2d.drawString(mainText, xMain, yMain);

        // Progress bar
        int barWidth = (int) (width * 0.5);
        int barHeight = 30;
        int barX = (width - barWidth) / 2;
        int barY = yMain + 100;
        int filledWidth = (int) (barWidth * ((double) (totalDays - daysLeft) / totalDays));

        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRoundRect(barX, barY, barWidth, barHeight, 15, 15);
        g2d.setColor(new Color(gradient[0].getRed(), gradient[0].getGreen(), gradient[0].getBlue()));
        g2d.fillRoundRect(barX, barY, filledWidth, barHeight, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(barX, barY, barWidth, barHeight, 15, 15);

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("countdown_image.png"));
            System.out.println("Image saved as countdown_image.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Color[] getGradientColors(String gradientType) {
        switch (gradientType) {
            case "soft_sunrise":
                return new Color[]{new Color(255, 255, 204), new Color(255, 204, 178)};
            case "morning_mist":
                return new Color[]{new Color(255, 230, 230), new Color(230, 204, 255)};
            case "blushing_clouds":
                return new Color[]{new Color(255, 255, 255), new Color(255, 204, 204)};
            case "minty_fresh":
                return new Color[]{new Color(173, 255, 247), new Color(144, 238, 144)};
            default:
                return null;
        }
    }
}

// Crafted with ❤️ by Ridham Kumar
