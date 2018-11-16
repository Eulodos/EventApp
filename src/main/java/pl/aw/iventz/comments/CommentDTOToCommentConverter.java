package pl.aw.iventz.comments;

import org.springframework.stereotype.Service;

@Service
public class CommentDTOToCommentConverter {


    public Comment convertDTOToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();

        comment.setContent(commentDTO.getContent());
        comment.setCommentCreationDate(commentDTO.getCreationDate());
        comment.setEvent(commentDTO.getEvent());
        comment.setCommentPoster(commentDTO.getCommentPoster());

        return comment;
    }
}
