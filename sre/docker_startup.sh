#!/bin/sh

# !表示对结果取反，表示找出所有用户不是 loan 的文件，最后的 + 表示将所有找出的文件一起执行 chown 命令
find "$TARGETS_DIR" \! -user work -exec chown 1000:1000 '{}' +
/bin/prometheus $@
