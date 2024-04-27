package net.integr.utilities


class Evaluator {
    companion object {
        fun evaluate(expression: String): Double {
            var result = 0.0
            var operation = ""

            val openBrackets: MutableList<Char> = ArrayList()
            val closedBrackets: MutableList<Char> = ArrayList()

            val innerInput = StringBuilder()

            for (element in expression) {
                if (openBrackets.isEmpty()) {
                    if (Character.isDigit(element)) {
                        if (operation == "" && result == 0.0) {
                            result = (element.digitToIntOrNull(Character.MAX_RADIX) ?: -1).toDouble()
                            continue
                        } else if (operation != "") {
                            result = calculateOperation(operation, (element.digitToIntOrNull(Character.MAX_RADIX) ?: -1).toDouble(), result)
                            continue
                        }
                    }
                    if (element == '+' || element == '-' || element == '*' || element == '/') {
                        operation = element.toString()
                        continue
                    }
                }

                if (element == '(') {
                    openBrackets.add(element)
                    continue
                }

                if (element == ')') {
                    closedBrackets.add(element)
                    if (openBrackets.size == closedBrackets.size) {
                        openBrackets.remove('(')
                        closedBrackets.remove(')')

                        val evalResult = evaluate(innerInput.toString())
                        result = calculateOperation(operation, evalResult, result)

                        innerInput.setLength(0)
                    }

                    if (openBrackets.size > closedBrackets.size) continue

                } else innerInput.append(element)
            }

            return result
        }

        private fun calculateOperation(operation: String, inputChar: Double, output: Double): Double {
            var outVar = output

            when (operation) {
                "+" -> outVar += inputChar
                "-" -> outVar -= inputChar
                "*" -> outVar *= inputChar
                "/" -> outVar /= inputChar
            }

            return outVar
        }
    }

}