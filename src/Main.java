import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String playerName;
    public static int fieldSize;

    public static void main(String[] args) {
        //Крестики-нолики
        //2. Проверка победы по диагоналям
        //3. Попробовать переписать программу так, чтобы она работала с разным равномерным размером полей.
        //При этом пользователю предоставляется возможность задать размер поля. Например, 5х5, 10х10 и так далее.
        // 4х3 считает недопустимым.

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Enter player name...");
        playerName = scanner.nextLine();

        fieldSize(scanner);

        play(scanner, random);
    }

    static void fieldSize(Scanner scanner){
        do {
            System.out.println("Enter the size of the array...");
            fieldSize = scanner.nextInt();
        } while (fieldSize<2);
    }

    static void play(Scanner scanner, Random random) {
        char[][] field = new char[fieldSize][fieldSize];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = '-';
            }
        }
        drawField(field);

        do {
            doPlayerMove(scanner, field);
            if (isFinal(field, 'X')) {
                break;
            }

            doAIMove(random, field);
            if (isFinal(field, 'O')) {
                break;
            }

            drawField(field);
        } while (true);

        System.out.println("Final score");
        drawField(field);
    }

    static boolean isFinal(char[][] field, char sign) {
        if (isWin(field, sign)) {
            String name = sign == 'X' ? playerName : "PC";
            System.out.println(String.format("%s won", name));
            return true;
        }
        if (isDraw(field)) {
            System.out.println("There is draw detected. No one won!");
            return true;
        }
        return false;
    }

    static boolean isWin(char[][] field, char sign) {
        // для фиксированного массива
//        for (int i = 0; i < field.length; i++){
//            if(field[i][0] == sign && field[i][1] == sign && field[i][2] == sign||
//                    field[0][i] == sign && field[1][i] == sign && field[2][i] == sign){
//                return true;
//            }
//            if(field[0][0] == sign && field[1][1] == sign && field[2][2] == sign||
//                    field[0][2] == sign && field[1][1] == sign && field[2][0] == sign){
//                return true;
//            }
//        }


        // по горизонтали
        for (int i = 0; i < field.length; i++) {
            int sum=0;
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == sign) sum++;
            }
            if(fieldSize==sum) return true;

        }

        // по вертикали
        for (int i = 0; i < field.length; i++) {
            int sum=0;
            for (int j = 0; j < field.length; j++) {
                if (field[j][i] == sign) sum++;
            }
            if(fieldSize==sum) return true;
        }

        //по диагонали i j
        int sum=0;
        for (int i = 0; i < field.length; i++) {

            for (int j = 0; j < field.length; j++) {
                if(field[i][j]==sign && i==j) sum++;
            }
            if(fieldSize==sum) return true;
        }

        //по диагонали i x
        int sum_i_x=0;
        for (int i = 0; i<field.length; i++) {
            for (int x = field.length-1, j = 0; j < field.length; j++, x--) {
                if(field[i][j]==sign && x==i) sum_i_x++;
            }
            if(fieldSize==sum_i_x) return true;
        }

        return false;
    }

    static boolean isDraw(char[][] field) {
        int count = field.length * field.length;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] != '-') {
                    count--;
                }
            }
        }
        return count == 0;
    }

    static void doAIMove(Random random, char[][] field) {
        int x, y;
        do {
            x = random.nextInt(fieldSize);
            y = random.nextInt(fieldSize);
        } while (field[x][y] != '-');
        field[x][y] = 'O';
    }

    static void doPlayerMove(Scanner scanner, char[][] field) {
        int x, y;
        do {
            x = getCoordinate(scanner, 'X');
            y = getCoordinate(scanner, 'Y');
        } while (field[x][y] != '-');
        field[x][y] = 'X';
    }

    static int getCoordinate(Scanner scanner, char coordName) {
        int coord;
        do {
            System.out.println(String.format("Please input %s-coordinate in range [1, %d] ...", coordName, fieldSize));
            coord = scanner.nextInt() - 1;
        } while (coord < 0 || coord > fieldSize);
        return coord;
    }

    static void drawField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }


}
