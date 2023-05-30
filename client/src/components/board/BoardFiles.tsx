import useFile from "hooks/useFile";
import { BsDownload } from "react-icons/bs";
import { BoardFile } from "types/board";

export default function BoardFiles({ files }: { files: BoardFile[] }) {
  const { downloadFile } = useFile();

  const handleDownload = (fileId: number) => downloadFile.mutate(fileId);

  return (
    <div className="pt-8">
      <h3 className="py-2">첨부파일</h3>
      <ul className="flex flex-col w-4/5 py-3 border border-gray-200">
        {files.map((file) => (
          <li
            className="flex justify-between items-center px-3 py-2 hover:bg-slate-100"
            key={file.id}
          >
            <span
              className="hover:cursor-pointer"
              onClick={() => handleDownload(file.id)}
            >
              {file.originFileName}
            </span>
            <button
              className="text-lg hover:cursor-pointer hover:text-brand"
              onClick={() => handleDownload(file.id)}
            >
              <BsDownload />
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}
