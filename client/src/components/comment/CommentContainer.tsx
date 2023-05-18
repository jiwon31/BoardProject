import useComment from "hooks/useComment";
import CommentInput from "./CommentInput";
import Comments from "./Comments";

export default function CommentContainer({ boardId }: { boardId: number }) {
  const {
    commentQuery: { isLoading, error, data: comments },
    createComment,
    updateComment,
    deleteComment,
  } = useComment(boardId);

  const create = (content: string, update: (content: string) => void) =>
    createComment.mutate(
      { boardId, content: { content } },
      {
        onSuccess: () => update(""),
        onError: (error) => alert(error.message),
      }
    );

  return (
    <div className="flex flex-col gap-y-16">
      <Comments
        isLoading={isLoading}
        error={error}
        comments={comments}
        updateComment={updateComment}
        deleteComment={deleteComment}
      />
      <CommentInput handleSubmit={create} />
    </div>
  );
}
