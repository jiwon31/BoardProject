import { useState, useEffect } from "react";
import { Button } from "../ui/Button";
import { BoardContent } from "types/board";
import useBoard from "hooks/board/useBoard";
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
  const [uploadedFiles, setUploadedFiles] = useState<File[]>([]);
  const navigate = useNavigate();

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setBoardInfo((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { files } = e.target;
    if (files && files.length > 0) {
      setUploadedFiles([...uploadedFiles, ...files]);
      return;
    }
    setUploadedFiles([]);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = setFormData();

    if (!boardId) {
      createBoard.mutate(formData, {
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

  function setFormData(): FormData {
    const formData = new FormData();
    formData.append(
      "request",
      new Blob([JSON.stringify(boardInfo)], { type: "application/json" })
    );
    if (uploadedFiles.length) {
      uploadedFiles.forEach((file) => formData.append("files", file));
    }
    return formData;
  }

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
        className="flex flex-col gap-y-5 w-full h-full"
        onSubmit={handleSubmit}
      >
        <div className="flex justify-between items-center mb-5 pb-5 border-b border-gray-200">
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
        <input
          className="block w-3/12 text-sm border-none pl-0 p-3 
          file:mr-4 file:py-2 file:px-4
          file:rounded-md file:border-0
          file:text-sm file:font-semibold
        file:bg-red-50 file:text-brand
        hover:file:bg-red-100"
          type="file"
          name="file"
          multiple
          onChange={handleFileChange}
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
