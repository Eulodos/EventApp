package pl.aw.iventz.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.aw.iventz.events.Event;
import pl.aw.iventz.users.login.UserLoginService;
import pl.aw.iventz.users.registration.User;
import pl.aw.iventz.users.registration.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private CommentDTOToCommentConverter commentDTOToCommentConverter;
    private UserLoginService loginService;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentDTOToCommentConverter commentDTOToCommentConverter, UserLoginService loginService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentDTOToCommentConverter = commentDTOToCommentConverter;
        this.loginService = loginService;
        this.userRepository = userRepository;
    }


    public void addNewComment(CommentDTO commentDTO, Event concreteEventById) {
        setUserAndEventForDTO(commentDTO, concreteEventById);
        Comment comment = commentDTOToCommentConverter.convertDTOToComment(commentDTO);
        commentRepository.save(comment);
    }

    public List<Comment> findAllCommentsForEventByEventId(Long id) {
//        allByEventId.sort((c1,c2) -> c1.getCommentCreationDate().compareTo(c2.getCommentCreationDate()));
//        allByEventId.sort(Comparator.comparing(Comment::getCommentCreationDate).reversed());
//        Collections.reverse(allByEventId);
        //todo: nauczyć się pisać taki kod
        return commentRepository.findAllByEventId(id).stream().sorted(Comparator.nullsLast(Comparator.comparing(Comment::getCommentCreationDate).reversed())).collect(Collectors.toList());
    }

    private void setUserAndEventForDTO(CommentDTO commentDTO, Event event) {
        commentDTO.setEvent(event);

        String userLoggedIn = loginService.getUserLoggedIn();
        Optional<User> userByEmail = userRepository.findUserByEmail(userLoggedIn);

        commentDTO.setCreationDate(new Date());
        commentDTO.setCommentPoster(userByEmail.get());
    }
}
