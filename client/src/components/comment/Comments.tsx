import useGetComments from "hooks/comment/useGetComments";
import CommentItem from "./CommentItem";

export default function Comments({ boardId }: { boardId: number }) {
  const {
    getCommentList: { isLoading, error, data: comments },
  } = useGetComments(boardId);

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
