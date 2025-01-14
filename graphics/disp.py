import pandas as pd
import numpy as np

data = {
    "Комбінація": [1, 2, 3, 4, 5, 6, 7, 8],
    "Втрати": [1462, 1714, 0, 0, 1406, 1711, 0, 0]
}

k = 2
n = 20

df = pd.DataFrame(data)
mean_y = df["Втрати"].mean()
factor_means = df.groupby(df.index // (len(df) // k))["Втрати"].mean()

S_f2 = sum((factor_means - mean_y)**2) / k

residuals = df["Втрати"] - mean_y
S_r2 = sum(residuals**2) / (k * (n - 1))

F = S_f2 / S_r2

print(S_f2, S_r2, F)
