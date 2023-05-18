import { UseMutationResult } from "@tanstack/react-query";
import { Button } from "components/ui/Button";
import { useState } from "react";
import { CreateCommentRequest } from "types/comment";

type CommentInputProps = {
  boardId: number;
  createComment: UseMutationResult<{ id: number }, Error, CreateCommentRequest>;
};

export default function CommentInput({
  boardId,
  createComment,
}: CommentInputProps) {
  const [content, setContent] = useState<string>("");

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
