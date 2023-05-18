import { Comment } from "types/comment";
import CommentItem from "./CommentItem";

type CommentsProps = {
  isLoading: boolean;
  error: Error | null;
  comments?: Comment[];
};

export default function Comments({
  isLoading,
  error,
  comments,
}: CommentsProps) {
  return (
    <div>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <ul className="flex flex-col gap-y-2">
        {comments &&
          comments.map((comment) => (
            <CommentItem key={comment.id} comment={comment} />
          ))}
      </ul>
    </div>
  );
}
