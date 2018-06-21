package hello.opencv;

import hello.PreviewFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ConfiguratorFrame extends JFrame implements ChangeListener{

    PreviewFrame mainFrame;

     private JPanel configurationPanel;
    JSlider treshold = new JSlider(JSlider.HORIZONTAL,
            0, 5000, 100);
    JSlider minValue = new JSlider(JSlider.HORIZONTAL,
            0, 500, 100);
    JSlider maxValue = new JSlider(JSlider.HORIZONTAL,
            0, 500, 100);
    JSlider backgroundRatio = new JSlider(JSlider.HORIZONTAL,
            0, 1, 0);
    List<JSlider> sliders = Arrays.asList(treshold, minValue, maxValue, backgroundRatio);

    public ConfiguratorFrame(PreviewFrame mainFrame) throws HeadlessException {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 600, 480);
        configurationPanel = new JPanel();
        setUpSliders(sliders);
        configurationPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(configurationPanel);
    }


    private void setUpSliders(List<JSlider> sliders){
        sliders.forEach(s -> setUpAndAddSlider(s));

    }

    private void setUpAndAddSlider(JSlider slider){
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.addChangeListener(this);
        this.configurationPanel.add(slider);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
//        JSlider source = (JSlider) e.getSource();
//        if (!source.getValueIsAdjusting()) {
//            int value = (int) source.getValue();
//            mainFrame.videoCap.setThreshold(value);
//        }
    }
}
