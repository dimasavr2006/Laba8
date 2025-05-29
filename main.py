import argparse
import os
from datetime import datetime

# Файлы с какими расширениями считать текстовыми и пытаться прочитать
# Можете дополнить этот список
TEXT_FILE_EXTENSIONS = {
    '.java', '.py', '.js', '.ts', '.html', '.css', '.scss', '.xml', '.json',
    '.yaml', '.yml', '.properties', '.ini', '.conf', '.cfg', '.txt', '.md',
    '.sh', '.bat', '.ps1', '.gradle', '.sql', '.c', '.cpp', '.h', '.hpp',
    '.cs', '.go', '.rb', '.php', '.swift', '.kt', '.kts'
}

# Директории, которые следует игнорировать (часто содержат много мусора или сгенерированных файлов)
# Можете дополнить
IGNORE_DIRS = {
    '.git', '.idea', '.vscode', 'node_modules', 'bower_components',
    'vendor', 'target', 'build', 'dist', 'out', '__pycache__', '.gradle',
    'bin', 'obj'
}

# Файлы, которые следует игнорировать по имени (не по расширению)
IGNORE_FILES = {
    '.DS_Store', 'Thumbs.db'
}

# Максимальный размер файла в байтах, который будем пытаться прочитать как текст
# (чтобы не пытаться читать огромные логи или бинарники, ошибочно принятые за текст)
MAX_TEXT_FILE_SIZE = 1 * 1024 * 1024  # 1 MB

def get_project_structure(project_path, output_file_path, max_depth=None, include_content=True):
    """
    Собирает структуру проекта и содержимое текстовых файлов.

    :param project_path: Путь к корневой директории проекта.
    :param output_file_path: Путь к файлу, куда будет записан результат.
    :param max_depth: Максимальная глубина вложенности директорий для обхода. None - без ограничений.
    :param include_content: Включать ли содержимое текстовых файлов.
    """
    if not os.path.isdir(project_path):
        print(f"Ошибка: Директория '{project_path}' не найдена.")
        return

    with open(output_file_path, 'w', encoding='utf-8') as outfile:
        outfile.write(f"Структура проекта: {os.path.abspath(project_path)}\n")
        outfile.write(f"Сгенерировано: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
        outfile.write("=" * 80 + "\n\n")

        for root, dirs, files in os.walk(project_path, topdown=True):
            # Фильтрация игнорируемых директорий
            # Модифицируем dirs на месте, чтобы os.walk не заходил в них
            dirs[:] = [d for d in dirs if d not in IGNORE_DIRS]

            level = root.replace(project_path, '').count(os.sep)
            if max_depth is not None and level > max_depth:
                dirs[:] = [] # Не углубляемся дальше
                continue

            indent = ' ' * 4 * level
            outfile.write(f"{indent}📂 {os.path.basename(root) if root != project_path else project_path}/\n")
            sub_indent = ' ' * 4 * (level + 1)

            for f_name in sorted(files):
                if f_name in IGNORE_FILES:
                    continue

                file_path = os.path.join(root, f_name)
                file_ext = os.path.splitext(f_name)[1].lower()
                file_size = os.path.getsize(file_path)

                outfile.write(f"{sub_indent}📄 {f_name} ({file_size} bytes)\n")

                if include_content and file_ext in TEXT_FILE_EXTENSIONS:
                    if file_size == 0:
                        outfile.write(f"{sub_indent}  [Содержимое файла пустое]\n\n")
                    elif file_size > MAX_TEXT_FILE_SIZE:
                        outfile.write(f"{sub_indent}  [Файл слишком большой для отображения содержимого (>{MAX_TEXT_FILE_SIZE // 1024} KB)]\n\n")
                    else:
                        try:
                            with open(file_path, 'r', encoding='utf-8', errors='ignore') as infile:
                                content = infile.read()
                            outfile.write(f"{sub_indent}  --- СОДЕРЖИМОЕ ФАЙЛА ({f_name}) ---\n")
                            # Добавляем отступ к каждой строке содержимого для лучшей читаемости
                            for line in content.splitlines():
                                outfile.write(f"{sub_indent}  | {line}\n")
                            outfile.write(f"{sub_indent}  --- КОНЕЦ СОДЕРЖИМОГО ({f_name}) ---\n\n")
                        except Exception as e:
                            outfile.write(f"{sub_indent}  [Не удалось прочитать содержимое файла: {e}]\n\n")
                elif include_content: # Если не текстовый, но контент запрошен
                     outfile.write(f"{sub_indent}  [Файл не является текстовым (расширение: {file_ext}) или слишком большой для отображения содержимого]\n\n")


    print(f"Структура проекта и содержимое текстовых файлов сохранены в: {os.path.abspath(output_file_path)}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Собирает структуру проекта и содержимое текстовых файлов в один отчет.",
        formatter_class=argparse.RawTextHelpFormatter
    )
    parser.add_argument("project_path", help="Путь к корневой директории проекта.")
    parser.add_argument(
        "-o", "--output",
        default="project_structure_report.txt",
        help="Имя выходного файла для отчета (по умолчанию: project_structure_report.txt)."
    )
    parser.add_argument(
        "--max-depth",
        type=int,
        default=None,
        help="Максимальная глубина вложенности директорий для анализа (по умолчанию: без ограничений)."
    )
    parser.add_argument(
        "--no-content",
        action="store_false", # По умолчанию True, этот флаг сделает его False
        dest="include_content",
        help="Не включать содержимое файлов, только структуру."
    )
    parser.add_argument(
        "--add-ext",
        action="append",
        default=[],
        help="Добавить расширения файлов, которые считать текстовыми (например, .log). Можно использовать несколько раз."
    )
    parser.add_argument(
        "--add-ignore-dir",
        action="append",
        default=[],
        help="Добавить имя директории для игнорирования (например, my_temp_dir). Можно использовать несколько раз."
    )
    parser.add_argument(
        "--add-ignore-file",
        action="append",
        default=[],
        help="Добавить имя файла для игнорирования (например, temp.dat). Можно использовать несколько раз."
    )


    args = parser.parse_args()

    # Обновляем глобальные наборы на основе аргументов командной строки
    if args.add_ext:
        for ext in args.add_ext:
            TEXT_FILE_EXTENSIONS.add(ext if ext.startswith('.') else '.' + ext)
    if args.add_ignore_dir:
        IGNORE_DIRS.update(args.add_ignore_dir)
    if args.add_ignore_file:
        IGNORE_FILES.update(args.add_ignore_file)

    get_project_structure(args.project_path, args.output, args.max_depth, args.include_content)