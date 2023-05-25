import { Button } from "components/ui/Button";
import { useState } from "react";

type CommentInputProps = {
  commentContent?: string;
  handleSubmit: (
    content: string,
    update: (content: string) => void | undefined
  ) => void;
};

export default function CommentInput({
  commentContent,
  handleSubmit,
}: CommentInputProps) {
  const [content, setContent] = useState<string>(commentContent || "");

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) =>
    setContent(e.target.value);

  return (
    <div className="flex flex-col border border-gray-300 p-3 bg-white">
      <textarea
        className="border-none mb-5"
        name="content"
        value={content}
        placeholder="댓글을 입력해주세요"
        required
        onChange={handleChange}
      />
      <div className="flex flex-row justify-end">
        <Button
          text="등록"
          disabled={!content}
          onClick={() => handleSubmit(content, setContent)}
        />
      </div>
    </div>
  );
}
