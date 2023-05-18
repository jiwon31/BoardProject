import useComment from "hooks/useComment";
import CommentInput from "./CommentInput";
import Comments from "./Comments";

export default function CommentContainer({ boardId }: { boardId: number }) {
  const {
    commentQuery: { isLoading, error, data: comments },
    createComment,
  } = useComment(boardId);

  return (
    <div className="flex flex-col gap-y-16">
      <Comments isLoading={isLoading} error={error} comments={comments} />
      <CommentInput boardId={boardId} createComment={createComment} />
    </div>
  );
}
