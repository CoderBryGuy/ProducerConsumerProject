package com.company;

import java.util.List;

public class Consumer implements Runnable{

    List<Integer> questionsList = null;


    public Consumer(List<Integer> questionsList) {
        this.questionsList = questionsList;
    }

    public void answerQuestion() throws InterruptedException{
        synchronized (questionsList) {
            while (questionsList.isEmpty()) {
                System.out.println("No Question to Answer ..wait for producer to get questions");
                questionsList.wait();
            }
        }

        synchronized (questionsList){
            Thread.sleep(5000);
            System.out.println("ANSWERED Question: " + questionsList.remove(0));
            questionsList.notify();
        }

    }

    @Override
    public void run() {
        while(true){
            try {
                answerQuestion();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}
