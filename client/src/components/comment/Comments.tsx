import { Comment, CommentContent, UpdateCommentRequest } from "types/comment";
import CommentItem from "./CommentItem";
import { UseMutationResult } from "@tanstack/react-query";

type CommentsProps = {
  isLoading: boolean;
  error: Error | null;
  comments?: Comment[];
  updateComment: UseMutationResult<CommentContent, Error, UpdateCommentRequest>;
  deleteComment: UseMutationResult<void, Error, number>;
};

export default function Comments({
  isLoading,
  error,
  comments,
  updateComment,
  deleteComment,
}: CommentsProps) {
  return (
    <div>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <ul className="flex flex-col gap-y-2">
        {comments &&
          comments.map((comment) => (
            <CommentItem
              key={comment.id}
              comment={comment}
              updateComment={updateComment}
              deleteComment={deleteComment}
            />
          ))}
      </ul>
    </div>
  );
}
