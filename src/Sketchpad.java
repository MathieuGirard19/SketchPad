import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.security.Key;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class Sketchpad extends JFrame implements
        ActionListener, // menu item
        ItemListener,		// radio buttons
        MouseListener,		// pressing/releasing a mouse button
        MouseMotionListener,	// dragging or moving a mouse
        WindowListener		// closing a window
{
    DrawingCanvas canvas = new DrawingCanvas();

    public Sketchpad() {
        getContentPane().add(canvas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        addWindowListener(this);
        this.setMenu();
        setVisible(true);

    }

    public void setMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu shapes = new JMenu("Shapes");
        JMenuItem draw = new JMenuItem("Draw");
        draw.addActionListener(this);
        JMenuItem line = new JMenuItem("Line");
        line.addActionListener(this);
        JMenuItem rectangle = new JMenuItem("Rectangle");
        rectangle.addActionListener(this);
        JMenuItem ellipse = new JMenuItem("Ellipses");
        ellipse.addActionListener(this);
        JMenuItem square = new JMenuItem("Square");
        square.addActionListener(this);
        JMenuItem circle = new JMenuItem("Circle");
        circle.addActionListener(this);
        JMenuItem polygon = new JMenuItem("Polygon");
        polygon.addActionListener(this);
        shapes.add(draw);
        shapes.add(line);
        shapes.add(rectangle);
        shapes.add(ellipse);
        shapes.add(square);
        shapes.add(circle);
        shapes.add(polygon);

        //File menu
        JMenu file = new JMenu("File");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        file.add(save);
        file.add(load);
        file.add(undo);
        file.add(redo);

        //color menu
        JMenu colors = new JMenu("Colors");
        JMenuItem black = new JMenuItem("Black");
        black.addActionListener(this);
        JMenuItem red = new JMenuItem("Red");
        red.addActionListener(this);
        JMenuItem blue = new JMenuItem("Blue");
        blue.addActionListener(this);
        JMenuItem yellow = new JMenuItem("Yellow");
        yellow.addActionListener(this);
        colors.add(black);
        colors.add(red);
        colors.add(blue);
        colors.add(yellow);

        //add all menu dropdowns
        menuBar.add(file);
        menuBar.add(shapes);
        menuBar.add(colors);
        this.setJMenuBar(menuBar);
    }

    public static void main(String arg[]) {
        new Sketchpad();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem)e.getSource();
        String menuItemText = menuItem.getText();
        if(menuItemText.equals("Red") || menuItemText.equals("Yellow") || menuItemText.equals("Blue") || menuItemText.equals("Black")){
            switch(menuItemText){
                case "Red":
                    canvas.colorChoice = Color.RED;
                    break;
                case "Yellow":
                    canvas.colorChoice = Color.YELLOW;
                    break;
                case "Blue":
                    canvas.colorChoice = Color.BLUE;
                    break;
                default:
                    canvas.colorChoice = Color.BLACK;
            }
        }
        else{
            canvas.shapeChoice = menuItemText;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    class DrawingCanvas extends Canvas implements KeyListener {
        double x, y;
        int x1, y1, x2, y2;

        Color colorChoice = Color.black;
        Shape selectedShape = null;
        String shapeChoice = "Cirlce";
        Shape deletedShape = null;
        ArrayList<Shape> shapeList = new ArrayList<Shape>();
        ArrayList<Shape> drawList = new ArrayList<Shape>();
        Boolean firstTime = true;
        Boolean isShapeSelected = false;
        private Image image;
        private Graphics gDraw;

        public DrawingCanvas() {
            setBackground(Color.white);
            MyMouseListener myMouseListener = new MyMouseListener();
            addKeyListener(this);
            addMouseListener(myMouseListener);
            addMouseMotionListener(myMouseListener);
            setSize(400, 300);
        }

        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setColor(colorChoice);
            if (firstTime){
                firstTime = false;
                return;
            }
            if(shapeChoice == "Draw"){
                Shape newLine = new Line2D.Double(x1, y1, x2, y2);
                drawList.add(newLine);
            }
            else if (isShapeSelected){
                double shapeWidth = selectedShape.getBounds2D().getWidth();
                double shapeHeight = selectedShape.getBounds2D().getHeight();
                for(int i=0; i<shapeList.size(); i++){
                    if (selectedShape == shapeList.get(i)){
                        if(selectedShape.getClass() == Rectangle2D.Double.class){
                            shapeList.set(i, new Rectangle2D.Double(x2, y2, shapeWidth, shapeHeight));
                            selectedShape = new Rectangle2D.Double(x2, y2, shapeWidth, shapeHeight);
                        }
                        else if(selectedShape.getClass() == Ellipse2D.Double.class){
                            shapeList.set(i, new Ellipse2D.Double(x2, y2, shapeWidth, shapeHeight));
                            selectedShape = new Ellipse2D.Double(x2, y2, shapeWidth, shapeHeight);
                        }
                        else if(selectedShape.getClass() == Line2D.Double.class){
                            shapeList.set(i, new Line2D.Double(x2, y2, x2+shapeWidth, y2+shapeHeight));
                            selectedShape = new Line2D.Double(x2, y2, x2+shapeWidth, y2+shapeHeight);
                        }
                    }
                }
            }
            else{
                int absX = Math.abs(x1 - x2);
                int absY = Math.abs(y1 - y2);
                switch (shapeChoice){
                    case "Ellipses":
                        Shape newEllipses = new Ellipse2D.Double(x1, y1, absX, absY);
                        shapeList.add(newEllipses);
                        break;
                    case "Circle":
                        Shape newCircle = new Ellipse2D.Double(x1, y1, absX, absX);
                        shapeList.add(newCircle);
                        break;
                    case "Rectangle":
                        Shape newRect = new Rectangle2D.Double(x1, y1, absX, absY);
                        shapeList.add(newRect);
                        break;
                    case "Square":
                        Shape newSquare = new Rectangle2D.Double(x1, y1, absX, absX);
                        shapeList.add(newSquare);
                        break;
                    case "Line":
                        Shape newLine = new Line2D.Double(x1, y1, x2, y2);
                        shapeList.add(newLine);
                        break;
                    default:
                        break;
                }
            }

            for(int i=0; i<shapeList.size(); i++){
                g2D.draw(shapeList.get(i));
            }
            for(int i=0; i<drawList.size(); i++){
                g2D.draw(drawList.get(i));
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == 8){
                if(selectedShape != null){
                    for(int i=0; i<shapeList.size(); i++){
                        if (shapeList.get(i).toString().equals(selectedShape.toString())){
                            deletedShape = selectedShape;
                            shapeList.remove(i);
                            canvas.repaint();
                        }
                    }
                }
            }
            else if(e.getKeyCode() == 10){
                if(deletedShape != null){
                    shapeList.add(deletedShape);
                    deletedShape = null;
                    canvas.repaint();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        class MyMouseListener extends MouseInputAdapter {
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                for(int i=0; i<shapeList.size(); i++){
                    if(shapeList.get(i).contains(x1, y1)){
                        selectedShape = shapeList.get(i);
                        isShapeSelected = true;
                        break;
                    }
                    else if(shapeList.get(i).getClass() == Line2D.Double.class){
                        Rectangle2D clickBounds = new Rectangle2D.Double(x1, y1, 2, 2);
                        if(shapeList.get(i).intersects(clickBounds)){
                            selectedShape = shapeList.get(i);
                            isShapeSelected = true;
                            break;
                        }
                    }
                    else{
                        isShapeSelected = false;
                    }
                }

            }

            public void mouseDragged(MouseEvent e) {
                if (shapeChoice == "Draw") {
                    x2 = e.getX();
                    y2 = e.getY();
                    canvas.repaint();
                    x1 = x2;
                    y1 = y2;
                }
            }

            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                canvas.repaint();
            }
        }
    }
}
