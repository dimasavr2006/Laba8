//package gui;
//
//import classes.Coordinates;
//import classes.HumanBeing;
//import gui.dialogs.ViewHumanDialog;
//import run.Main;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Ellipse2D;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//public class VisualizationPanel extends JPanel {
//
//    private List<HumanBeing> humanBeingsToDraw;
//    private Map<Integer, Color> userColorMap;
//    private Random randomColorGenerator;
//    private Color myUserColor = Color.BLUE;
//    private int currentUserId = -1;
//
//    private Map<Integer, Integer> objectAnimationRadius;
//    private Timer animationTimer;
//    private static final int ANIMATION_STEPS = 20;
//    private static final int ANIMATION_DELAY = 25;
//
//    private double minX, maxX, minY, maxY;
//    private boolean boundsCalculated = false;
//
//    private static final int OBJECT_DIAMETER = 20; // Диаметр круга
//
//    private static final int PADDING = 30;
//
//    private List<Shape> drawnObjectShapes;
//    private List<HumanBeing> drawnObjectsList;
//
//
//    public VisualizationPanel() {
//        this.humanBeingsToDraw = new ArrayList<>();
//        this.userColorMap = new HashMap<>();
//        this.randomColorGenerator = new Random();
//        setBackground(Color.WHITE);
//
//        if (Main.username != null && !Main.username.isEmpty()) {
//            this.currentUserId = Main.db.findUserIDbyUsername(Main.username);
//        }
//        this.objectAnimationRadius = new HashMap<>();
//
//        animationTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                boolean needsRepaint = false;
//                for (HumanBeing human : humanBeingsToDraw) { // Итерируемся по текущим объектам
//                    int id = human.getId();
//                    int currentRadius = objectAnimationRadius.getOrDefault(id, 0);
//                    if (currentRadius < OBJECT_DIAMETER) {
//                        currentRadius += OBJECT_DIAMETER / ANIMATION_STEPS; // Увеличиваем радиус
//                        if (currentRadius > OBJECT_DIAMETER) {
//                            currentRadius = OBJECT_DIAMETER;
//                        }
//                        objectAnimationRadius.put(id, currentRadius);
//                        needsRepaint = true;
//                    }
//                }
//                if (needsRepaint) {
//                    repaint();
//                }
//            }
//        });
//        animationTimer.start();
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getButton() == MouseEvent.BUTTON1) { // Реагируем только на левую кнопку мыши
//                    Point clickPoint = e.getPoint();
//                    // Итерируемся по нарисованным объектам в обратном порядке,
//                    // чтобы сначала проверять те, что "сверху"
//                    for (int i = drawnObjectShapes.size() - 1; i >= 0; i--) {
//                        Shape shape = drawnObjectShapes.get(i);
//                        if (shape.contains(clickPoint)) {
//                            HumanBeing clickedHuman = drawnObjectsList.get(i);
//                            System.out.println("Клик на объекте ID: " + clickedHuman.getId()); // Отладка
//                            // Открываем ViewHumanDialog
//                            // Получаем родительское окно MainWindow
//                            Frame mainWindow = (Frame) SwingUtilities.getWindowAncestor(VisualizationPanel.this);
//                            if (mainWindow != null) {
//                                ViewHumanDialog viewDialog = new ViewHumanDialog(mainWindow, clickedHuman);
//                                viewDialog.setVisible(true);
//
//                            }
//                            break; // Объект найден, выходим из цикла
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    public void setHumanBeings(List<HumanBeing> humanBeings) {
//        if (humanBeings != null) {
//            this.humanBeingsToDraw = new ArrayList<>(humanBeings);
//        } else {
//            this.humanBeingsToDraw = new ArrayList<>();
//        }
//        for (HumanBeing newHuman : this.humanBeingsToDraw) {
//            objectAnimationRadius.putIfAbsent(newHuman.getId(), 0);
//        }
//        calculateBounds();
//        repaint();
//    }
//
//    private void calculateBounds() {
//        if (humanBeingsToDraw.isEmpty()) {
//            boundsCalculated = false;
//            return;
//        }
//
//        minX = Double.MAX_VALUE; maxX = Double.MIN_VALUE;
//        minY = Double.MAX_VALUE; maxY = Double.MIN_VALUE;
//
//        for (HumanBeing human : humanBeingsToDraw) {
//            if (human.getCoordinates() != null) {
//                minX = Math.min(minX, human.getCoordinates().getX());
//                maxX = Math.max(maxX, human.getCoordinates().getX());
//                minY = Math.min(minY, human.getCoordinates().getY());
//                maxY = Math.max(maxY, human.getCoordinates().getY());
//            }
//        }
//
//        if (minX == maxX) {
//            minX -= 1;
//            maxX += 1;
//        }
//        if (minY == maxY) {
//            minY -= 1;
//            maxY += 1;
//        }
//        boundsCalculated = true;
//    }
//
//    private Color getColorForUser(int ownerId) {
//        if (ownerId == currentUserId) {
//            return myUserColor;
//        }
//        return userColorMap.computeIfAbsent(ownerId, k -> new Color(
//                randomColorGenerator.nextInt(200) + 25, // 25-225, чтобы не слишком темные/светлые
//                randomColorGenerator.nextInt(200) + 25,
//                randomColorGenerator.nextInt(200) + 25));
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g.create();
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        if (humanBeingsToDraw.isEmpty() || !boundsCalculated) {
//            String noDataMessage = "Нет данных для визуализации";
//            FontMetrics fm = g2d.getFontMetrics();
//            int stringWidth = fm.stringWidth(noDataMessage);
//            int stringAscent = fm.getAscent();
//            int x = (getWidth() - stringWidth) / 2;
//            int y = (getHeight() + stringAscent) / 2 - fm.getDescent();
//            g2d.drawString(noDataMessage, x, y);
//            g2d.dispose();
//            return;
//        }
//
//        int drawableWidth = getWidth() - 2 * PADDING;
//        int drawableHeight = getHeight() - 2 * PADDING;
//
//        if (drawableWidth <= 0 || drawableHeight <= 0) {
//            g2d.dispose();
//            return;
//        }
//
//        double scaleX = drawableWidth / (maxX - minX);
//        double scaleY = drawableHeight / (maxY - minY);
//
//        for (HumanBeing human : humanBeingsToDraw) {
//            if (human.getCoordinates() == null) continue;
//
//            Coordinates coords = human.getCoordinates();
//
//            int screenX = PADDING + (int) ((coords.getX() - minX) * scaleX);
//            int screenY = PADDING + (int) ((coords.getY() - minY) * scaleY);
//
//
//            Color objectColor = getColorForUser(human.getOwnerId());
//            g2d.setColor(objectColor);
//
//            g2d.fillOval(screenX - OBJECT_DIAMETER / 2, screenY - OBJECT_DIAMETER / 2, OBJECT_DIAMETER, OBJECT_DIAMETER);
//
//            g2d.setColor(Color.BLACK);
//            g2d.drawOval(screenX - OBJECT_DIAMETER / 2, screenY - OBJECT_DIAMETER / 2, OBJECT_DIAMETER, OBJECT_DIAMETER);
//
//             g2d.setColor(Color.DARK_GRAY);
//             g2d.drawString(String.valueOf(human.getId()), screenX + OBJECT_DIAMETER / 2 + 2, screenY + OBJECT_DIAMETER / 4);
//
//            int animatedDiameter = objectAnimationRadius.getOrDefault(human.getId(), OBJECT_DIAMETER);
//
//            g2d.setColor(getColorForUser(human.getOwnerId()));
//            g2d.fillOval(screenX - animatedDiameter / 2, screenY - animatedDiameter / 2, animatedDiameter, animatedDiameter);
//
//            g2d.setColor(Color.BLACK);
//            g2d.drawOval(screenX - animatedDiameter / 2, screenY - animatedDiameter / 2, animatedDiameter, animatedDiameter);
//
//        }
//        g2d.dispose();
//    }
//}

package gui;

import classes.Coordinates;
import classes.HumanBeing;
import gui.dialogs.ViewHumanDialog;
import managers.LocalisationManager;
import run.Main;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

public class VisualizationPanel extends JPanel {

    private List<HumanBeing> humanBeingsToDraw;
    private Map<Integer, Color> userColorMap; // Для цвета пользователя
    private Random randomColorGenerator;
    private Color myUserColor = Color.BLUE; // Цвет объектов текущего пользователя
    private int currentUserId = -1;

    // Для масштабирования и правильного отображения координат
    private double dataMinX, dataMaxX, dataMinY, dataMaxY; // Границы данных
    private boolean boundsHaveBeenCalculated = false;

    // Параметры объектов и анимации
    private static final int TARGET_OBJECT_DIAMETER = 20; // Целевой диаметр объекта
    private static final int PADDING = 30; // Отступы от краев панели
    private static final int ANIMATION_TOTAL_STEPS = 20; // Количество шагов для анимации "вырастания"
    private static final int ANIMATION_FRAME_DELAY = 25; // мс между кадрами анимации (~40 FPS)

    // Хранение экранных представлений объектов для кликов
    private List<Shape> drawnObjectScreenShapes;
    private List<HumanBeing> drawnObjectsReferences;

    // Для анимации "вырастания"
    private Timer animationTimer;
    private Map<Integer, AnimationState> objectAnimationStates; // ID объекта -> его состояние анимации

    // Внутренний класс для состояния анимации
    private static class AnimationState {
        int currentDiameter;
        boolean isAppearing; // true, если объект сейчас в процессе "появления"

        AnimationState() {
            this.currentDiameter = 0; // Начинаем с нулевого размера
            this.isAppearing = true;
        }
    }

    public VisualizationPanel() {
        this.humanBeingsToDraw = new ArrayList<>();
        this.userColorMap = new HashMap<>();
        this.randomColorGenerator = new Random();
        this.drawnObjectScreenShapes = new ArrayList<>();
        this.drawnObjectsReferences = new ArrayList<>();
        this.objectAnimationStates = new HashMap<>();

        setBackground(Color.LIGHT_GRAY); // Немного другой фон для наглядности

        // Получаем ID текущего пользователя (если он уже известен)
        if (Main.username != null && !Main.username.isEmpty() && Main.db != null) {
            this.currentUserId = Main.db.findUserIDbyUsername(Main.username);
        }

        // Таймер для анимации
        animationTimer = new Timer(ANIMATION_FRAME_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean needsRepaint = false;
                // Важно: итерироваться по ключам objectAnimationStates, чтобы избежать ConcurrentModificationException,
                // если мы будем удалять из него элементы внутри цикла. Но лучше удалять после.
                // Или использовать копию ключей.
                List<Integer> idsToAnimate = new ArrayList<>(objectAnimationStates.keySet());

                for (Integer humanId : idsToAnimate) {
                    AnimationState state = objectAnimationStates.get(humanId);
                    if (state != null && state.isAppearing && state.currentDiameter < TARGET_OBJECT_DIAMETER) {
                        state.currentDiameter += Math.max(1, TARGET_OBJECT_DIAMETER / ANIMATION_TOTAL_STEPS);
                        if (state.currentDiameter >= TARGET_OBJECT_DIAMETER) {
                            state.currentDiameter = TARGET_OBJECT_DIAMETER;
                            state.isAppearing = false; // Анимация завершена
                        }
                        // objectAnimationStates.put(humanId, state); // Map обновится по ссылке, но для ясности можно
                        needsRepaint = true;
                    }
                }

                if (needsRepaint) {
                    repaint();
                }
                // Если все анимации завершены, таймер можно остановить, но для простоты пока оставим его работать.
            }
        });
        animationTimer.start();

        // Обработчик кликов мыши
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Левая кнопка мыши
                    Point clickPoint = e.getPoint();
                    // Итерируемся в обратном порядке, чтобы сначала проверять объекты "сверху"
                    for (int i = drawnObjectScreenShapes.size() - 1; i >= 0; i--) {
                        Shape shape = drawnObjectScreenShapes.get(i);
                        if (shape != null && shape.contains(clickPoint)) {
                            HumanBeing clickedHuman = drawnObjectsReferences.get(i);
                            // Открываем диалог просмотра для этого объекта
                            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(VisualizationPanel.this);
                            ViewHumanDialog viewDialog = new ViewHumanDialog(parentFrame, clickedHuman);
                            viewDialog.setVisible(true);

                            // Если ViewHumanDialog изменил данные (например, удалил или отредактировал),
                            // то MainWindow должен будет обновить всю информацию.
                            // ViewHumanDialog теперь сам вызывает обновление MainWindow.
                            break; // Объект найден и обработан
                        }
                    }
                }
            }
        });
    }

    public void setHumanBeings(List<HumanBeing> humanBeings) {
        if (humanBeings != null) {
            this.humanBeingsToDraw = new ArrayList<>(humanBeings);
        } else {
            this.humanBeingsToDraw = new ArrayList<>();
        }

        // Создаем новую карту состояний анимации на основе текущего списка объектов
        Map<Integer, AnimationState> nextAnimationStates = new HashMap<>();
        for (HumanBeing human : this.humanBeingsToDraw) {
            AnimationState existingState = this.objectAnimationStates.get(human.getId());
            if (existingState != null) {
                // Если объект уже был, переносим его состояние анимации
                // Если он уже появился, его currentDiameter должен быть TARGET_OBJECT_DIAMETER
                if (!existingState.isAppearing) {
                    existingState.currentDiameter = TARGET_OBJECT_DIAMETER;
                }
                nextAnimationStates.put(human.getId(), existingState);
            } else {
                // Это новый объект, создаем для него новое состояние анимации (начнется с 0)
                nextAnimationStates.put(human.getId(), new AnimationState());
            }
        }
        this.objectAnimationStates = nextAnimationStates; // Заменяем старую карту новой

        calculateDataBounds();
        if (!animationTimer.isRunning() && !this.humanBeingsToDraw.isEmpty()) {
            // Запускаем таймер, если он был остановлен и есть что анимировать
            // animationTimer.start(); // Обычно он и так работает
        }
        repaint();
    }

    private void calculateDataBounds() {
        if (humanBeingsToDraw.isEmpty()) {
            boundsHaveBeenCalculated = false;
            return;
        }

        dataMinX = Double.MAX_VALUE; dataMaxX = Double.MIN_VALUE;
        dataMinY = Double.MAX_VALUE; dataMaxY = Double.MIN_VALUE;

        for (HumanBeing human : humanBeingsToDraw) {
            if (human.getCoordinates() != null) {
                dataMinX = Math.min(dataMinX, human.getCoordinates().getX());
                dataMaxX = Math.max(dataMaxX, human.getCoordinates().getX());
                dataMinY = Math.min(dataMinY, human.getCoordinates().getY());
                dataMaxY = Math.max(dataMaxY, human.getCoordinates().getY());
            }
        }

        // Если все объекты в одной точке или нет объектов с координатами
        if (dataMinX == Double.MAX_VALUE || dataMinX == dataMaxX) {
            dataMinX = (dataMinX == Double.MAX_VALUE) ? -5 : dataMinX - 5; // Добавляем диапазон
            dataMaxX = dataMinX + 10;
        }
        if (dataMinY == Double.MAX_VALUE || dataMinY == dataMaxY) {
            dataMinY = (dataMinY == Double.MAX_VALUE) ? -5 : dataMinY - 5; // Добавляем диапазон
            dataMaxY = dataMinY + 10;
        }
        boundsHaveBeenCalculated = true;
    }

    private Color getColorForUser(int ownerId) {
        if (ownerId == currentUserId && currentUserId != -1) { // Проверяем, что currentUserId инициализирован
            return myUserColor;
        }
        return userColorMap.computeIfAbsent(ownerId, k -> new Color(
                randomColorGenerator.nextInt(180) + 50, // от 50 до 229 для более различимых цветов
                randomColorGenerator.nextInt(180) + 50,
                randomColorGenerator.nextInt(180) + 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create(); // Работаем с копией Graphics для безопасности
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Очищаем списки перед каждой перерисовкой (важно!)
        drawnObjectScreenShapes.clear();
        drawnObjectsReferences.clear();

        if (humanBeingsToDraw.isEmpty() || !boundsHaveBeenCalculated) {
            String message = LocalisationManager.getString("visualization.noData"); // "Нет данных для визуализации"
            FontMetrics fm = g2d.getFontMetrics();
            int stringWidth = fm.stringWidth(message);
            g2d.drawString(message, (getWidth() - stringWidth) / 2, getHeight() / 2);
            g2d.dispose();
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int drawableWidth = panelWidth - 2 * PADDING;
        int drawableHeight = panelHeight - 2 * PADDING;

        if (drawableWidth <= 0 || drawableHeight <= 0) { // Панель слишком мала
            g2d.dispose();
            return;
        }

        double dataRangeX = dataMaxX - dataMinX;
        double dataRangeY = dataMaxY - dataMinY;
        // Избегаем деления на ноль, если все объекты в одной точке (уже обработано в calculateDataBounds)

        double scaleX = (dataRangeX == 0) ? 1 : drawableWidth / dataRangeX;
        double scaleY = (dataRangeY == 0) ? 1 : drawableHeight / dataRangeY;

        // Чтобы сохранить пропорции, можно выбрать минимальный масштаб
        // double scale = Math.min(scaleX, scaleY);
        // scaleX = scale;
        // scaleY = scale;

        for (HumanBeing human : humanBeingsToDraw) {
            if (human.getCoordinates() == null) continue;

            Coordinates coords = human.getCoordinates();
            AnimationState animState = objectAnimationStates.getOrDefault(human.getId(), new AnimationState()); // Получаем или создаем состояние
            if(animState.currentDiameter == 0 && animState.isAppearing) { // Если объект новый и анимация не началась
                objectAnimationStates.put(human.getId(), animState); // Добавляем в карту для анимации
            }


            int currentAnimatedDiameter = animState.currentDiameter;
            if (currentAnimatedDiameter <= 0) continue; // Если еще не начал появляться, не рисуем

            // Преобразование мировых координат (где Y растет вверх) в экранные (где Y растет вниз)
            int screenX = PADDING + (int) ((coords.getX() - dataMinX) * scaleX);
            // Инвертируем Y: значение (Y - minY) масштабируется, а затем вычитается из drawableHeight
            int screenY = PADDING + drawableHeight - (int) ((coords.getY() - dataMinY) * scaleY);


            Ellipse2D.Double objectShape = new Ellipse2D.Double(
                    screenX - currentAnimatedDiameter / 2.0,
                    screenY - currentAnimatedDiameter / 2.0,
                    currentAnimatedDiameter,
                    currentAnimatedDiameter);

            drawnObjectScreenShapes.add(objectShape);
            drawnObjectsReferences.add(human);

            g2d.setColor(getColorForUser(human.getOwnerId()));
            g2d.fill(objectShape);

            g2d.setColor(Color.DARK_GRAY); // Темнее, чем черный, для лучшего контраста с цветными кругами
            g2d.draw(objectShape);

            // Опционально: ID объекта
            String idStr = String.valueOf(human.getId());
            FontMetrics fm = g2d.getFontMetrics();
            int idWidth = fm.stringWidth(idStr);
            // Рисуем текст по центру круга
            g2d.setColor(Color.BLACK); // Цвет текста
            // Небольшое смещение, чтобы текст был внутри круга и по центру
            g2d.drawString(idStr, screenX - idWidth / 2, screenY + fm.getAscent() / 2 - fm.getDescent() / 2 - 1);
        }
        g2d.dispose();
    }
}