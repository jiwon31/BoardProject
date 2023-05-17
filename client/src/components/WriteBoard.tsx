import { useState, useEffect } from "react";
import { Button } from "./ui/Button";
import { BoardContent } from "types/board";
import useBoard from "hooks/useBoard";
import { useNavigate, useParams } from "react-router-dom";

const initialBoardInfo = {
  title: "",
  content: "",
};

export default function WriteBoard({ text }: { text: string }) {
  const { id } = useParams();
  const boardId = parseInt(id!);
  const {
    singleBoardQuery: { data: board },
    createBoard,
    updateBoard,
  } = useBoard(boardId);
  const [boardInfo, setBoardInfo] = useState<BoardContent>(initialBoardInfo);
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!boardId) {
      createBoard.mutate(boardInfo, {
        onSuccess: (data) => navigate(`/boards/${data.id}`),
        onError: (error) => alert(error.message),
      });
    } else {
      updateBoard.mutate(
        { id: boardId, info: boardInfo },
        {
          onSuccess: () => navigate(`/boards/${boardId}`),
          onError: (error) => alert(error.message),
        }
      );
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setBoardInfo((prev) => ({ ...prev, [name]: value }));
  };

  /** 새로고침 대응 */
  useEffect(() => {
    if (board) {
      setBoardInfo((prev) => ({
        ...prev,
        title: board.title,
        content: board.content,
      }));
    }
  }, [board]);

  return (
    <section className="py-10 max-w-5xl mx-auto h-screen">
      <form
        className="flex flex-col gap-y-10 w-full h-full"
        onSubmit={handleSubmit}
      >
        <div className="flex justify-between items-center pb-5 border-b border-gray-200">
          <h1 className="text-2xl font-bold">{text}</h1>
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
