package com.company;

import java.util.List;

public class Producer implements Runnable{

    List<Integer> questionsList = null;
    final int LIMIT = 5;
    private int questionNo;

    public Producer(List<Integer> questionsList) {
        this.questionsList = questionsList;
    }

    public void readQuestion(int questionNo) throws InterruptedException{

        synchronized (questionsList) {
            while (questionsList.size() == LIMIT) {
                System.out.println("Questions have piled up ..wait for answers");
                questionsList.wait();
            }
        }

        synchronized (questionsList){
            System.out.println("New Question: " + questionNo);
            questionsList.add(questionNo);
            Thread.sleep(100);
            questionsList.notify();
        }

    }

    @Override
    public void run() {
        while(true){
            try {
                readQuestion(questionNo++);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}
