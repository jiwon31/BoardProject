import { useMutation } from "@tanstack/react-query";
import FileApi from "api/file-api";

export default function useFile(fileApi = new FileApi()) {
  const downloadFile = useMutation(
    (fileId: number) => fileApi.downloadFile(fileId),
    {
      onSuccess: (res) => {
        const url = window.URL.createObjectURL(new Blob([res.data]));

        const link = document.createElement("a");
        link.href = url;
        link.style.display = "none";

        const disposition = res.headers["content-disposition"];
        const fileName = decodeURI(
          disposition
            .match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)[1]
            .replace(/['"]/g, "")
        );
        link.download = fileName;

        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
      },
    }
  );

  return { downloadFile };
}
