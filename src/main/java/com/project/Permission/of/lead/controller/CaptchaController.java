package com.project.Permission.of.lead.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController

public class CaptchaController {



    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int width = 200;
        int height = 80;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        Random rand = new Random();

        // 🔹 Background gradient
        GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, width, height, Color.LIGHT_GRAY);
        g.setPaint(gp);
        g.fillRect(0, 0, width, height);

        // 🔹 Generate random captcha text
        String captcha = generateCaptchaText(6);

        // 🔹 Store in session (clear old first)
        request.getSession().removeAttribute("captcha");
        request.getSession().setAttribute("captcha", captcha);

        // 🔹 Fonts
        String[] fonts = {"Arial", "Courier", "TimesRoman", "Verdana"};

        for (int i = 0; i < captcha.length(); i++) {

            // Random font
            Font font = new Font(
                    fonts[rand.nextInt(fonts.length)],
                    Font.BOLD,
                    30 + rand.nextInt(10)
            );
            g.setFont(font);

            // Random color
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));

            // Rotation
            double angle = (rand.nextInt(40) - 20) * Math.PI / 180;
            g.rotate(angle, 30 * i + 15, 40);

            // Draw character
            g.drawString(String.valueOf(captcha.charAt(i)), 30 * i + 15, 50);

            g.rotate(-angle, 30 * i + 15, 40);
        }

        // 🔹 Noise lines
        for (int i = 0; i < 10; i++) {
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g.drawLine(rand.nextInt(width), rand.nextInt(height),
                    rand.nextInt(width), rand.nextInt(height));
        }

        // 🔹 Noise dots
        for (int i = 0; i < 500; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            image.setRGB(x, y, rand.nextInt());
        }



        g.dispose();

        // 🔹 Prevent caching
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/png");

        ImageIO.write(image, "png", response.getOutputStream());
    }

    // 🔹 Generate random text
    private String generateCaptchaText(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

}
