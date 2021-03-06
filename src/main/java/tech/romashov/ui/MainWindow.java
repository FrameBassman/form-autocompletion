package tech.romashov.ui;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import org.slf4j.Logger;
import tech.romashov.ApplicationProperties;
import tech.romashov.bl.SummaryForm;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainWindow(Logger logger)
    {
        super("TestContentPane");

        SelenideConfig config = new SelenideConfig();
        SelenideDriver selenide = new SelenideDriver(config);

        ApplicationProperties properties = new ApplicationProperties(logger, FileSystems.getDefault());

        this.setName("TestContentPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Создание панели с двумя кнопками
        JPanel contents = new JPanel();
        JButton openBrowserButton = new JButton("Открываем браузер");
        openBrowserButton.setName("OpenBrowserButton");
        openBrowserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Get url from properties");
                String url = properties.getProperty("url");
                logger.info("Finish getting url from properties");

                logger.info("Start browser opening with url {}", url);
                selenide.open(url);
                logger.info("Finish browser opening");

                selenide.switchTo().frame("main");
                logger.info("Finish browser opening");
            }
        });
        JButton fillFormButton = new JButton("Заполняем форму");
        fillFormButton.setName("OpenBrowserButton");
        fillFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SummaryForm form = new SummaryForm(logger, selenide, properties);
                form.fill();
            }
        });

        contents.add(openBrowserButton);
        contents.add(fillFormButton);
        setContentPane(contents);
        setSize(200, 100);
        // Открытие окна
        setVisible(true);
        setAlwaysOnTop(true);
    }
}
