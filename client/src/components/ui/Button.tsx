import { CgSpinner } from "react-icons/cg";

type ButtonProps = {
  text: string;
  onClick?: () => void;
  disabled?: boolean;
  type?: "button" | "submit" | "reset";
  isLoading?: boolean;
};

export const Button = ({
  text,
  onClick,
  disabled,
  type,
  isLoading,
}: ButtonProps) => {
  return (
    <button
      className="px-4 py-2 text-white bg-brand rounded-sm hover:brightness-110 disabled:bg-zinc-400"
      onClick={onClick}
      disabled={disabled}
      type={type || "button"}
    >
      {isLoading && (
        <CgSpinner className="animate-spin inline-block w-6 h-6 mr-2" />
      )}
      {!isLoading && text}
    </button>
  );
};
