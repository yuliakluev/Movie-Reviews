/*
 *
 */
package edu.hdsb.gwss.yulia.ics4u;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Movie Review Assignment
 *
 * @author Wm.Muir
 * @version 2017-18.S2
 */
public class MovieReview {

    public static void main(String[] args) throws Exception {

        // MOVIE REVIEW FILE
        File reviews = new File(".\\data\\movie.review\\MovieReviews.txt");
        Scanner input = new Scanner(System.in);

       /* System.out.println("What would you like to do?");
        System.out.println("1 : Get the score of a word");
        System.out.println("2 : Get the average score of words in a file");
        System.out.println("3 : Find the highest/lowest scoring words in a file");
        System.out.println("4 : Sort the words from a file into positive text and negative text");
        System.out.println("5 : Exit the program");
        */
        System.out.println(menu());

        int choice = Integer.parseInt(input.nextLine());

        if (choice == 1) {

            //MAKE SURE TO ACCOUNT FOR DIVISION BY ZERO
            System.out.println("Enter a word you would like to search for");
            String word = input.nextLine().toLowerCase();

            int wordCount = wordCount(word, reviews);
            double wordAverage = wordAverage(word, reviews);

            System.out.println("The word " + word + " appears " + wordCount + " times");

            if (wordCount == -1) {
                System.out.println("This word appears 0 times. It has no average score");
            } else {
                System.out.println("The average score of this word in the file is " + wordAverage);
            }

            System.out.println(menu());
            choice = Integer.parseInt(input.nextLine());
            
        }

        if (choice == 2) {

            System.out.println("Give the name of a file containing a series of words, one-per-line, to find the average score of the words in the file.");

            File fileOfChoice = new File(".\\data\\movie.review\\" + input.nextLine());

            double sentenceAverage = sentenceAverage(fileOfChoice, reviews);

            System.out.println("The average score of the sentence provided in the file is " + sentenceAverage);
            
            System.out.println(menu());
            choice = Integer.parseInt(input.nextLine());

        }

        if (choice == 3) {

            System.out.println("Give the name of a file containing a series of words, one-per-line, from which you would like to find the highesst scoring and lowest scoring words.");
            File fileOfChoice = new File(".\\data\\movie.review\\" + input.nextLine());

            System.out.println(highestScore(fileOfChoice, reviews));
            System.out.println(lowestScore(fileOfChoice, reviews));
            
            System.out.println(menu());
            choice = Integer.parseInt(input.nextLine());

        }

        if (choice == 4) {

            System.out.println("Enter the file name in which you would like the words to be sorted from positive to negative");
            File fileOfChoice = new File(".\\data\\movie.review\\" + input.nextLine());

            positive(fileOfChoice, reviews);
            negative(fileOfChoice, reviews);
            
            System.out.println(menu());
            choice = Integer.parseInt(input.nextLine());

        }
        if (choice == 5) {

        }

    }
    
    public static String menu(){
        
        return"What would you like to do? \n 1 : Get the score of a word \n 2 : Get the average score of words in a file \n 3 : Find the highest/lowest scoring words in a file \n 4 : Sort the words from a file into positive text and negative text \n 5 : Exit the program";

    }
   /* public static int menuChoice(){
        int choice;
        
        return choice;
    }*/
    /**
     * This method will count the number of reviews that contain the key word.
     *
     * @param word the key word the review must contain.
     * @param reviews the file that contains the movie reviews.
     * @return the number of reviews that contain the key work at least once.
     * @throws java.lang.Exception
     */
    public static int wordCount(String word, File reviews) throws Exception {

        Scanner movieFile = new Scanner(reviews);
        String line;
        int wordCount = 0;
        int wordCountPerLine = 0;

        while (movieFile.hasNextLine()) {
            wordCountPerLine = 0;
            line = movieFile.nextLine();
            StringTokenizer st = new StringTokenizer(line);

            while (st.hasMoreTokens()) {

                if (st.nextToken().toLowerCase().equals(word) && wordCountPerLine < 1) {

                    wordCountPerLine++;
                    wordCount++;

                }

            }
        }
        return wordCount;

    }

    /**
     * This method will accumulate the the movie review scores that contain the
     * key word.
     *
     * @param word the key word the review must contain.
     * @param reviews the file that contains the movie reviews.
     * @return the sum of the scores for reviews that contain the key work at
     * least once.
     */
    public static int wordTotalScore(String word, File reviews) throws Exception {

        Scanner movieFile = new Scanner(reviews);
        String line;
        int scoreSum = 0;
        int score;

        while (movieFile.hasNextLine()) {
            int wordCountPerLine = 0;
            line = movieFile.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            score = Integer.parseInt(st.nextToken());

            while (st.hasMoreTokens()) {

                if (st.nextToken().toLowerCase().equals(word.toLowerCase()) && wordCountPerLine < 1) {

                    scoreSum = scoreSum + score;
                    wordCountPerLine++;
                }

            }
        }

        return scoreSum;
    }

    /**
     * Average score of reviews containing that word, given the specified file.
     *
     * @param word the key word the review must contain.
     * @param reviews reviews the file that contains the movie reviews.
     * @return the average score for the key word. Word Total Score / Word Count
     */
    public static double wordAverage(String word, File reviews) throws Exception {

        double wordCount = (double) wordCount(word, reviews);
        double wordTotalScore = (double) wordTotalScore(word, reviews);
        double wordAverage;
        //jqhfeia
        if (wordCount == 0) {
            wordAverage = -1.0;
        } else {
            wordAverage = wordTotalScore / wordCount;
        }

        return wordAverage;

    }

    /**
     * This method returns the average movie review score of the words in the
     * file, given th specified movie review file.
     */
    public static double sentenceAverage(File wordList, File reviews) throws Exception {

        Scanner movieFile = new Scanner(reviews);
        Scanner file = new Scanner(wordList);
        double wordCount = 0.0;
        double sentenceScore = 0.0;
        String line;
        double wordScore;

        while (file.hasNextLine()) {

            line = file.nextLine();
            wordScore = wordAverage(line, reviews);
            wordCount++;
            sentenceScore = sentenceScore + wordScore;

        }

        double sentenceAverage = sentenceScore / wordCount;

        return sentenceAverage;

    }

    public static String highestScore(File wordList, File reviews) throws Exception {

        Scanner file = new Scanner(wordList);
        String lineOne = file.nextLine();
        String lineTwo;
        String highestWord = lineOne;

        double wordScoreMax = wordAverage(lineOne.toLowerCase(), reviews);
        double wordScoreCompare;

        
        while (file.hasNextLine()) {
            
            lineTwo = file.nextLine();
            wordScoreCompare = wordAverage(lineTwo.toLowerCase(), reviews);
            
            if (wordScoreMax == -1) {
            highestWord = file.nextLine();
            wordScoreMax = wordAverage(highestWord.toLowerCase(), reviews);

             }
            if (wordScoreCompare == -1) {
            lineTwo = file.nextLine();
            wordScoreCompare = wordAverage(lineTwo.toLowerCase(), reviews);

            }
            //in case the word you are comparing never appears in the movie review file
            if (wordScoreCompare > wordScoreMax) {
                wordScoreMax = wordScoreCompare;
                highestWord = lineTwo;
                   
            }
        
            
        }
        return "The highest scoring word is " + highestWord + " with a score of " + wordScoreMax;
    }

   public static String lowestScore(File wordList, File reviews) throws Exception {

        Scanner file = new Scanner(wordList);
        String lineOne = file.nextLine();
        String lineTwo;
        String lowestWord = lineOne;

        double wordScoreMin = wordAverage(lineOne.toLowerCase(), reviews);
        double wordScoreCompare;

        
        while (file.hasNextLine()) {
            
            lineTwo = file.nextLine();
            wordScoreCompare = wordAverage(lineTwo.toLowerCase(), reviews);
            
            if (wordScoreMin == -1) {
            lowestWord = file.nextLine();
            wordScoreMin = wordAverage(lowestWord.toLowerCase(), reviews);

             }
            if (wordScoreCompare == -1) {
            lineTwo = file.nextLine();
            wordScoreCompare = wordAverage(lineTwo.toLowerCase(), reviews);

            }
            //in case the word you are comparing never appears in the movie review file
            if (wordScoreCompare < wordScoreMin) {
                wordScoreMin = wordScoreCompare;
                lowestWord = lineTwo;
                   
            }
        
            
        }
        return "The highest scoring word is " + lowestWord + " with a score of " + wordScoreMin;
    }


    public static void positive(File sentence, File reviews) throws FileNotFoundException, Exception {

        Scanner file = new Scanner(sentence);
        String word;
        double wordAverage;
        PrintWriter writer = new PrintWriter(".\\data\\movie.review\\positive.txt");

        while (file.hasNextLine()) {

            word = file.nextLine();
            wordAverage = wordAverage(word, reviews);
            //  String wordListPositive = "";

            if (wordAverage > 2.1) {

                writer.print(word + "\n");

            }
  

        }
                  writer.close();
    }

    public static void negative(File sentence, File reviews) throws FileNotFoundException, Exception {

        Scanner file = new Scanner(sentence);
        String word;
        double wordAverage;
        PrintWriter writer = new PrintWriter(".\\data\\movie.review\\negative.txt");

        while (file.hasNextLine()) {

            word = file.nextLine();
            wordAverage = wordAverage(word, reviews);

            if (wordAverage < 1.9) {

                writer.print(word + "\n");

            }

        }
                  writer.close();
    }

}
/* if(wordScoreCompare == wordScoreMin){
                
                    lowestWord = lowestWord + lineTwo;
                }
 */
