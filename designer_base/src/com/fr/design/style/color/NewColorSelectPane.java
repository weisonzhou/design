package com.fr.design.style.color;

import com.fr.design.border.UIRoundedBorder;
import com.fr.design.constants.UIConstants;
import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.ibutton.UIButton;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.mainframe.DesignerContext;
import com.fr.general.Inter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * @author zhou
 * @since 2012-5-29上午10:39:35
 */
public class NewColorSelectPane extends BasicPane implements ColorSelectable {
    private static final long serialVersionUID = -8634152305687249392L;

    private Color color = null; // color
    // color setting action.
    private ArrayList<ChangeListener> colorChangeListenerList = new ArrayList<ChangeListener>();

    // 颜色选择器
    private ColorSelectDetailPane pane;
    // 是否支持透明
    private boolean isSupportTransparent;

    private final static int TRANSPANENT_WINDOW_HEIGHT = 165;
    private final static int WINDWO_HEIGHT = 150;

    // 最近使用颜色
    private UsedColorPane usedColorPane;

    /**
     * Constructor.
     */
    public NewColorSelectPane() {
        this(false);
    }

    /**
     * Constructor.
     */
    public NewColorSelectPane(boolean isSupportTransparent) {
        this.isSupportTransparent = isSupportTransparent;
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        this.setBorder(new UIRoundedBorder(UIConstants.LINE_COLOR, 1, 5));
        if (isSupportTransparent) {
            UIButton transpanrentButton = new UIButton(Inter.getLocText("FR-Designer_ChartF-Transparency"));
            this.add(transpanrentButton, BorderLayout.NORTH);
            transpanrentButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    doTransparent();
                }
            });
        }

        // center
        JPanel centerPane = FRGUIPaneFactory.createY_AXISBoxInnerContainer_S_Pane();
        this.add(centerPane, BorderLayout.CENTER);

        // 第一行
        JPanel row1Pane = new JPanel(FRGUIPaneFactory.createBorderLayout());
        row1Pane.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 0));
        row1Pane.setPreferredSize(new Dimension(135, 16));  // 宽度为 16 * 8 + 7
        centerPane.add(row1Pane);
        // 最近使用
        usedColorPane = new UsedColorPane(1, 8, 1, this, true, false);
        usedColorPane.getPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        row1Pane.add(usedColorPane.getPane());

        JPanel menuColorPane1 = new JPanel();
        centerPane.add(menuColorPane1);

        menuColorPane1.setLayout(new GridLayout(5, 8, 1, 1));
        menuColorPane1.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        for (int i = 0; i < ColorFactory.MenuColors.length; i++) {
            menuColorPane1.add(new ColorCell(ColorFactory.MenuColors[i], this));
        }

        centerPane.add(Box.createVerticalStrut(1));

        // mod by anchore 16/11/16
        UIButton customButton = new UIButton(Inter.getLocText("FR-Designer-Basic_More_Color"));

        customButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                customButtonPressed();
            }
        });
        customButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPanel centerPane1 = FRGUIPaneFactory.createBorderLayout_S_Pane();
        centerPane1.setBorder(BorderFactory.createEmptyBorder(2, 8, 0, 8));
        centerPane1.add(customButton, BorderLayout.NORTH);
        centerPane.add(centerPane1);
    }


    /**
     * 添加监听
     *
     * @param 监听列表 Add change listener.
     */
    public void addChangeListener(ChangeListener changeListener) {
        this.colorChangeListenerList.add(changeListener);
    }

    @Override
    protected String title4PopupWindow() {
        return "Color";
    }

    /**
     * 获取颜色
     *
     * @return 颜色
     * Return the color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * 获取颜色
     *
     * @return 颜色
     */
    public Color getNotNoneColor() {
        if (color == null) {
            setColor(Color.WHITE);
            return Color.WHITE;
        }
        return color;
    }

    /**
     * Set the color.
     *
     * @param color the new color.
     */
    @Override
    public void setColor(Color color) {
        this.color = color;

        // fire color change.
        if (!colorChangeListenerList.isEmpty()) {
            ChangeEvent evt = new ChangeEvent(this);

            for (int i = 0; i < colorChangeListenerList.size(); i++) {
                this.colorChangeListenerList.get(i).stateChanged(evt);
            }
        }
        ColorSelectConfigManager.getInstance().addToColorQueue(color);
        this.repaint();
    }

    /**
     * 设置颜色
     *
     * @param 颜色位置
     */
    @Override
    public void colorSetted(ColorCell colorCell) {
        colorCell.repaint();
    }

    protected void doTransparent() {
        setColor(null);
    }

    protected void customButtonPressed() {
        pane = new ColorSelectDetailPane(Color.WHITE);
        ColorSelectDialog.showDialog(DesignerContext.getDesignerFrame(), pane, Color.WHITE, this);
    }

    @Override
    public Dimension getPreferredSize() {
        if (isSupportTransparent) {
            return new Dimension(super.getPreferredSize().width, TRANSPANENT_WINDOW_HEIGHT);
        }
        return new Dimension(super.getPreferredSize().width, WINDWO_HEIGHT);
    }

    /**
     * 更新最近使用颜色
     */
    public void updateUsedColor() {
        usedColorPane.updateUsedColor();
    }


}