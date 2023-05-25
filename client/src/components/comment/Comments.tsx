import CommentItem from "./CommentItem";
import useGetComment from "hooks/comment/useGetComment";

export default function Comments({ boardId }: { boardId: number }) {
  const {
    getCommentList: { isLoading, error, data: comments },
  } = useGetComment(boardId);

  return (
    <div>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong 😣</p>}
      <ul className="flex flex-col gap-y-2">
        {comments &&
          comments.map((comment) => (
            <CommentItem key={comment.id} comment={comment} boardId={boardId} />
          ))}
      </ul>
    </div>
  );
}
