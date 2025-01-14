import pandas as pd
import matplotlib.pyplot as plt

files = ['simulation_results_1.csv', 'simulation_results_2.csv', 'simulation_results_3.csv', 'simulation_results_4.csv']
labels = ["Прогін 1", "Прогін 2", "Прогін 3", "Прогін 4"]

plt.figure(figsize=(10, 6))
for i, file in enumerate(files):
    data = pd.read_csv(file)
    plt.plot(data["Time"], data["Response"], label=labels[i])

plt.title("Динаміка зміни відгуку моделі для 4 прогонів")
plt.xlabel("Час моделювання, мс")
plt.ylabel("Кількість втрачених пакетів, одиниць")
plt.legend()
plt.grid()
plt.show()
