package GUI;

import Pieces.Play;
import Pieces.PlayingPiece;
import Pieces.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class TableGUI {
    private static JFrame gameFrame;
    private final BoardPanel boardPanel;

    private PlayingPiece sourceTile;
    private static String pieceIconPath = "art/pieces/";

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private final Color darkTileColor = new Color(65, 43, 37);
    private final Color lightTileColor = new Color(170, 143, 119);

    public TableGUI() {
        gameFrame = new JFrame("Chess Game");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem newGameMenuItem = new JMenuItem("New Game");
        final JMenuItem exitMenuItem = new JMenuItem("Exit");

        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table.resetTable();
                Play.turn = 1;
                boardPanel.drawBoard();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newGameMenuItem);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }


    public static void endGame(String winner) {
        Object[] options = {"Start new game",
                "Exit",};
        int n = JOptionPane.showOptionDialog(gameFrame,
                winner + " win",
                "Game over",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (n == JOptionPane.YES_OPTION) {
            Table.resetTable();

        } else if (n == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    public static void checkMateMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }


    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    final TilePanel tilePanel = new TilePanel(this, row, col);
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }

            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard() {
            removeAll();
            for (int i = 0; i < boardTiles.size(); i++) {
                boardTiles.get(i).drawTile();
                add(boardTiles.get(i));
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileX;
        private final int tileY;

        TilePanel(final BoardPanel boardPanel, final int tileX, final int tileY) {
            super(new GridBagLayout());
            this.tileX = tileX;
            this.tileY = tileY;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon();
            mouseClickedAction();
            validate();
        }

        private void mouseClickedAction() {
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //right button deselects tile
                    if (isRightMouseButton(e)) {
                        if (sourceTile != null) {
                            clearBorder(boardPanel.boardTiles.get(sourceTile.getX() * 8 + sourceTile.getY()));
                        }
                        sourceTile = null;
                    } else if (isLeftMouseButton(e)) {
                        actionOnLeftMouseButton();
                    }
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
            });
        }

        private void actionOnLeftMouseButton() {
            if (sourceTile == null) {
                //first time clicking the left button
                sourceTile = Table.getObject(tileX, tileY);
                if (sourceTile == null || sourceTile == Table.getEmptyObject()) {
                    sourceTile = null;
                } else {
                    assignTileBorder();
                    Play.firstChessCheck();
                }
            } else {
                //second time clicking the left button
                clearBorder(boardPanel.boardTiles.get(sourceTile.getX() * 8 + sourceTile.getY()));
                PlayingPiece.setIsOnlyTesting(true);

                if (!Play.isSameColor(tileX, tileY) && sourceTile.moveIsLegal(tileX, tileY)) {
                    PlayingPiece.setIsOnlyTesting(false);
                    Play.tryToMakeMove(sourceTile.getX(), sourceTile.getY(), tileX, tileY);
                }

                PlayingPiece.setIsOnlyTesting(false);
                boardPanel.drawBoard();

                if (Play.madeMove) {
                    int tileIndex = sourceTile.getX() * 8 + sourceTile.getY();
                    int newTileIndex = tileX * 8 + tileY;
                    boardPanel.boardTiles.add(newTileIndex, boardPanel.boardTiles.get(tileIndex));
                    boardPanel.boardTiles.remove(tileIndex);
                    boardPanel.drawBoard();
                }

                Play.madeMove = false;
                sourceTile = null;
            }
        }

        void drawTile() {
            assignTileColor();
            assignTilePieceIcon();
            validate();
            repaint();
        }

        private void assignTileColor() {
            if (this.tileX % 2 == 0) {
                setBackground(this.tileY % 2 == 0 ? lightTileColor : darkTileColor);

            } else if (tileX % 2 != 0) {
                setBackground(this.tileY % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

        private void assignTileBorder() {
            setBorder(new LineBorder(Color.GREEN, 5));
        }

        private void clearBorder(TilePanel tilePanel) {
            tilePanel.setBorder(null);
        }


        private void assignTilePieceIcon() {
            this.removeAll();
            PlayingPiece tablePiece = Table.getObject(this.tileX, this.tileY);

            if (tablePiece != Table.getEmptyObject()) {
                try {
                    BufferedImage image = ImageIO.read(new File(pieceIconPath
                            + tablePiece.getColor().substring(0, 1).toUpperCase()
                            + tablePiece.getClass().getSimpleName().substring(0, 2).toUpperCase()
                            + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

