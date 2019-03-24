package ro.utcn.danf.a1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.danf.a1.exception.InvalidLoginCredentialException;
import ro.utcn.danf.a1.exception.ObjectNotFoundException;
import ro.utcn.danf.a1.exception.UserNotFoundException;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.service.PostManagementService;
import ro.utcn.danf.a1.service.TagManagementService;
import ro.utcn.danf.a1.service.UserManagementService;

import javax.persistence.Transient;
import java.util.Scanner;


@Component
@RequiredArgsConstructor
public class ConsoleController implements CommandLineRunner {
    private final UserManagementService userManagementService;
    private final PostManagementService postManagementService;
    private final TagManagementService tagManagementService;

    //TODO: Rename

    @Transient
    private final Scanner scanner = new Scanner(System.in);
    @Transient
    private SiteUser currentUser = null;

    @Override
    public void run(String... args) {
        System.out.println("Big Dan's bootleg site which is definitely not a clone of StackOverflow\n");
        System.out.println("Please login or else");
        boolean done = false;
        while (!done) {
            System.out.println("Your will is my command");
            String command = scanner.nextLine();
            try {
                done = handleCommand(command);
            } catch (UserNotFoundException userNotFoundException) {
                System.out.println("Now that is a name I have not heard in a long time...");
            } catch (ObjectNotFoundException e){
                System.out.println("Not found");
            } catch (InvalidLoginCredentialException e){
                System.out.println("Username or password invalid!");
            }
        }
    }

    private boolean handleCommand(String command)
    {   if(command.equals("login")) {
            handleLogin();
            return false;
        }
        if(currentUser==null) {
            System.out.println("You need to logged in to access this!");
            return false;
        }
        switch(command){
            case "exit":
                return true;
            case "browse":
                postManagementService.showQuestions().stream().forEach(x->System.out.println(x.toString()));
                return false;
            case "ask":
                handleAsk();
                return false;
            case "answer":
                handleAnswer();
                return false;
            case "search title":
                handleSearchByTitle();
                return false;
            case "search tag":
                handleFilterByTag();
                return false;
            case "create tag":
                handleCreateTag();
                return false;
            case "list tags":
                tagManagementService.showTags().forEach(x-> System.out.println(x.toString()));
                return false;
            case "add tag":
                handleAddTag();
                return false;
        }
        return false;
    }

    private void handleLogin(){
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        currentUser = userManagementService.userLogin(username,password);
    }

    private void handleAsk(){
        System.out.println("Title: ");
        String title = scanner.nextLine();
        System.out.println("Context: ");
        String context = scanner.nextLine();
        postManagementService.askQuestion(currentUser, title, context);
    }

    private void handleAnswer(){
        System.out.println("Question ID: ");
        int id = Integer.valueOf(scanner.nextLine());
        System.out.println("Title: ");
        String title = scanner.nextLine();
        System.out.println("Context: ");
        String context = scanner.nextLine();
        postManagementService.postAnswer(currentUser, id, title, context);
    }

    private void handleSearchByTitle(){
        System.out.println("Title: ");
        String title = scanner.nextLine();
        postManagementService.searchQuestionsByTitle(title).stream().forEach(x-> System.out.println(x.toString()));
    }

    private void handleCreateTag(){
        System.out.println("Tag Title: ");
        String title = scanner.nextLine();
        tagManagementService.createTag(title);
    }

    private void handleFilterByTag(){
        System.out.println("Tag Title: ");
        String title = scanner.nextLine();
        postManagementService.filterQuestionsByTag (title).forEach(x-> System.out.println(x.toString()));
    }

    private void handleAddTag(){
        System.out.println("Question ID: ");
        int id = Integer.valueOf(scanner.nextLine());
        System.out.println("Tag Title: ");
        String tagTitle = scanner.nextLine();
        tagManagementService.addTagToQuestion(tagTitle, id);
    }
}
