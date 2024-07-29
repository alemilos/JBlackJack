package controller;

import view.ExplanationPage;
import view.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExplanationController {

    private int page;

    private ExplanationPage explanationPage;

    private final int MAX_EXPLANATION_PAGE = 1;

    public ExplanationController(){
        this.explanationPage = new ExplanationPage();
        addActionListeners();
    }

    public void prev(){
       if (page > 0){
           page--;

           if (explanationPage != null){
               explanationPage.dispose();
           }

           explanationPage = new ExplanationPage(page);
           addActionListeners();
       }
    }

    public void next(){
        page++;

        if (explanationPage != null){
            explanationPage.dispose();
        }

        if (page <= MAX_EXPLANATION_PAGE){
            explanationPage = new ExplanationPage(page);
            addActionListeners();
        }else{
            new HomePage();
        }
    }

    /**
     * Update the action listeners for the buttons when page changes
     */
    private void addActionListeners(){
        explanationPage.getLeftBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prev();
            }
        });

        explanationPage.getRightBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });

    }
}
