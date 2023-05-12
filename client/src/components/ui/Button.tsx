type ButtonProps = {
  text: string;
  onClick?: () => void;
  disabled?: boolean;
  type?: "button" | "submit" | "reset";
};

export const Button = ({ text, onClick, disabled, type }: ButtonProps) => {
  return (
    <button
      className="px-4 py-2 text-white bg-brand rounded-sm hover:brightness-110 disabled:bg-zinc-400"
      onClick={onClick}
      disabled={disabled}
      type={type || "button"}
    >
      {text}
    </button>
  );
};
