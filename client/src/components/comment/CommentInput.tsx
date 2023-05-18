import { Button } from "components/ui/Button";
import useComment from "hooks/useComment";
import { useState } from "react";

export default function CommentInput({ boardId }: { boardId: number }) {
  const [content, setContent] = useState<string>("");
  const { createComment } = useComment();

  const handleSubmit = () =>
    createComment.mutate(
      { boardId, content: { content } },
      {
        onSuccess: () => setContent(""),
        onError: (error) => alert(error.message),
      }
    );

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) =>
    setContent(e.target.value);

  return (
    <div className="flex flex-col border border-gray-300 p-3">
      <textarea
        className="border-none mb-5"
        name="content"
        value={content}
        placeholder="댓글을 입력해주세요"
        required
        onChange={handleChange}
      />
      <div className="flex flex-row justify-end">
        <Button text="등록" disabled={!content} onClick={handleSubmit} />
      </div>
    </div>
  );
}
