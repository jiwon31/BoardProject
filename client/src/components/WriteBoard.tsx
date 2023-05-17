import { useState } from "react";
import { Button } from "./ui/Button";
import { BoardRequest } from "types/board";
import useBoard from "hooks/useBoard";
import { useNavigate } from "react-router-dom";

const initialBoardInfo = {
  title: "",
  content: "",
};

export default function WriteBoard() {
  const [boardInfo, setBoardInfo] = useState<BoardRequest>(initialBoardInfo);
  const { createBoard } = useBoard();
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    createBoard.mutate(boardInfo, {
      onSuccess: (data) => navigate(`/boards/${data.id}`),
      onError: (error) => alert(error.message),
    });
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setBoardInfo((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <section className="py-10 max-w-5xl mx-auto h-screen">
      <form
        className="flex flex-col gap-y-10 w-full h-full"
        onSubmit={handleSubmit}
      >
        <div className="flex justify-between items-center pb-5 border-b border-gray-200">
          <h1 className="text-2xl font-bold">글쓰기</h1>
          <Button
            text="완료"
            type="submit"
            disabled={Object.values(boardInfo).some((info) => !info)}
          />
        </div>
        <input
          name="title"
          value={boardInfo.title}
          placeholder="제목"
          required
          onChange={handleChange}
        />
        <textarea
          className="h-full"
          name="content"
          value={boardInfo.content}
          placeholder="내용을 입력해주세요"
          required
          onChange={handleChange}
        />
      </form>
    </section>
  );
}
