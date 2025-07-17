from flask import Flask, request, jsonify
import pandas as pd
from mlxtend.frequent_patterns import apriori, association_rules
from mlxtend.preprocessing import TransactionEncoder
import unicodedata
from flask import Response
import json
# Hàm chuẩn hóa tiếng Việt (bỏ dấu, viết hoa)
def normalize_text(text):
    text = str(text).strip()
    text = unicodedata.normalize('NFD', text)
    text = ''.join(c for c in text if unicodedata.category(c) != 'Mn')
    return text.upper()

# Load dữ liệu
df = pd.read_excel('src/resources/GiaoDich_SanPham_TiengViet.xlsx')
df['Itemname'] = df['Itemname'].apply(lambda x: [normalize_text(item) for item in str(x).split(',')])

# Transaction Encoding
te = TransactionEncoder()
te_ary = te.fit(df['Itemname']).transform(df['Itemname'])
df_encoded = pd.DataFrame(te_ary, columns=te.columns_)

# Chạy Apriori và luật kết hợp
frequent_itemsets = apriori(df_encoded, min_support=0.01, use_colnames=True)
rules = association_rules(frequent_itemsets, metric="confidence", min_threshold=0.5)

# Tạo API Flask
app = Flask(__name__)

@app.route('/recommend', methods=['GET'])
def recommend():
    product = request.args.get('product')

    if not product:
        return Response(
            json.dumps({'error': 'Thiếu tham số product'}, ensure_ascii=False, indent=2),
            mimetype='application/json; charset=utf-8'
        ), 400

    normalized_product = normalize_text(product)
    filtered_rules = rules[rules['antecedents'].apply(lambda x: normalized_product in x)]

    if filtered_rules.empty:
        return Response(
            json.dumps({'product': product, 'recommendations': []}, ensure_ascii=False, indent=2),
            mimetype='application/json; charset=utf-8'
        )

    recommended_items = set()
    for _, row in filtered_rules.iterrows():
        recommended_items.update(row['consequents'])

    result = {
        'product': product,
        'recommendations': list(recommended_items)
    }

    return Response(
        json.dumps(result, ensure_ascii=False, indent=2),
        mimetype='application/json; charset=utf-8'
    )



if __name__ == '__main__':
    app.run(debug=True)
